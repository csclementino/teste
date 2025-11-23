"use client"

import type React from "react"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import { Alert, AlertDescription } from "@/components/ui/alert"
import { Loader2, AlertCircle } from "lucide-react"

interface EquipeFormProps {
  token: string
  onSuccess: () => void
  onCancel: () => void
  equipeId?: string
  initialData?: {
    nome: string
    descricao: string
  }
}

export default function EquipeForm({ token, onSuccess, onCancel, equipeId, initialData }: EquipeFormProps) {
  const [nome, setNome] = useState(initialData?.nome || "")
  const [descricao, setDescricao] = useState(initialData?.descricao || "")
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState("")

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setError("")

    try {
    const method = equipeId ? "PUT" : "POST";
    const body = equipeId
      ? JSON.stringify({ id: equipeId, nome, descricao })
      : JSON.stringify({ nome, descricao });

    const response = await fetch("http://4.201.164.119:8080/equipe", {
      method,
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body,
    });

    if (!response.ok) {
      let errorMessage = `Erro ao ${equipeId ? "atualizar" : "criar"} equipe`;

      try {
        const text = await response.text();     
        const json = text ? JSON.parse(text) : {};

        if (json.mensagem) {
          errorMessage = json.mensagem;         
        }
      } catch {}

      throw new Error(errorMessage);
    }

    onSuccess();
  } catch (err) {
    setError(err instanceof Error ? err.message : "Erro ao processar equipe");
  } finally {
    setLoading(false);
  }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      {error && (
        <Alert variant="destructive" className="border-red-300 bg-red-50">
          <AlertCircle className="h-4 w-4" />
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      )}

      <div className="space-y-2">
        <Label htmlFor="nome" className="text-[#1a1a1a]">
          Nome da Equipe
        </Label>
        <Input
          id="nome"
          placeholder="ex: Equipe de Produção"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
          className="border-[#DDDDDD] focus:border-[#6C69EB] focus:ring-[#6C69EB]"
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="descricao" className="text-[#1a1a1a]">
          Descrição
        </Label>
        <Textarea
          id="descricao"
          placeholder="Descreva a função e responsabilidades da equipe"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
          required
          className="border-[#DDDDDD] focus:border-[#6C69EB] focus:ring-[#6C69EB]"
        />
      </div>

      <div className="flex gap-3">
        <Button type="submit" disabled={loading} className="bg-[#6C69EB] text-white hover:bg-[#5b5ad4]">
          {loading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
          {equipeId ? "Atualizar" : "Criar"} Equipe
        </Button>
        <Button type="button" onClick={onCancel} variant="outline" className="border-[#DDDDDD] bg-transparent">
          Cancelar
        </Button>
      </div>
    </form>
  )
}
