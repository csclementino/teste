"use client"

import type React from "react"

import { useState } from "react"
import { useRouter } from "next/navigation"
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Alert, AlertDescription } from "@/components/ui/alert"
import { Loader2, AlertCircle } from "lucide-react"

interface AuthModalProps {
  open: boolean
  onOpenChange: (open: boolean) => void
  mode: "login" | "register"
}

export default function AuthModal({ open, onOpenChange, mode: initialMode }: AuthModalProps) {
  const router = useRouter()
  const [mode, setMode] = useState<"login" | "register">(initialMode)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState("")

  // Login fields
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")

  // Register fields
  const [nome, setNome] = useState("")
  const [telefone, setTelefone] = useState("")
  const [registerPassword, setRegisterPassword] = useState("")

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setError("")

    try {
      const credentials = btoa(`${email}:${password}`)
      const response = await fetch("http://localhost:8080/auth", {
        method: "POST",
        headers: {
          Authorization: `Basic ${credentials}`,
          "Content-Type": "application/json",
        },
      })

      if (!response.ok) {
        throw new Error("Credenciais inválidas")
      }

      const data = await response.json()
      localStorage.setItem("access_token", data.access_token)

      onOpenChange(false)
      router.push("/dashboard")
    } catch (err) {
      setError(err instanceof Error ? err.message : "Erro ao fazer login")
    } finally {
      setLoading(false)
    }
  }

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setError("")

    try {
    const response = await fetch("http://localhost:8080/supervisor", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email,
        nome,
        telefone,
        senha: registerPassword,
      }),
    });

    if (!response.ok) {
      let errorMessage = "Erro ao criar conta";

      try {
        const text = await response.text();
        const json = text ? JSON.parse(text) : {};

        if (json.mensagem) {
          errorMessage = json.mensagem;
        }
      } catch {}

      throw new Error(errorMessage);
    }

    // --- LOGIN AUTOMÁTICO ---

    const credentials = btoa(`${email}:${registerPassword}`);
    const loginResponse = await fetch("http://localhost:8080/auth", {
      method: "POST",
      headers: {
        Authorization: `Basic ${credentials}`,
        "Content-Type": "application/json",
      },
    });

    if (!loginResponse.ok) {
      let loginErrorMessage = "Erro ao fazer login após registro";

      try {
        const text = await loginResponse.text();
        const json = text ? JSON.parse(text) : {};

        if (json.mensagem) {
          loginErrorMessage = json.mensagem;
        }
      } catch {}

      throw new Error(loginErrorMessage);
    }

    const loginData = await loginResponse.json();
    localStorage.setItem("access_token", loginData.access_token);

    onOpenChange(false);
    router.push("/dashboard");

  } catch (err) {
    setError(err instanceof Error ? err.message : "Erro ao criar conta");
  } finally {
    setLoading(false);
  }
  }

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="border-[#DDDDDD] bg-white">
        <DialogHeader>
          <DialogTitle className="text-[#6C69EB]">
            {mode === "login" ? "Entrar na Plataforma" : "Criar Nova Conta"}
          </DialogTitle>
          <DialogDescription>
            {mode === "login"
              ? "Acesse sua conta para gerenciar equipes e EPIs"
              : "Cadastre-se para começar a monitorar seus EPIs"}
          </DialogDescription>
        </DialogHeader>

        <form onSubmit={mode === "login" ? handleLogin : handleRegister} className="space-y-4">
          {error && (
            <Alert variant="destructive" className="border-red-300 bg-red-50">
              <AlertCircle className="h-4 w-4" />
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}

          {mode === "register" && (
            <div className="space-y-2">
              <Label htmlFor="nome" className="text-[#1a1a1a]">
                Nome Completo
              </Label>
              <Input
                id="nome"
                placeholder="Seu nome completo"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
                required
                className="border-[#DDDDDD] focus:border-[#6C69EB] focus:ring-[#6C69EB]"
              />
            </div>
          )}

          <div className="space-y-2">
            <Label htmlFor="email" className="text-[#1a1a1a]">
              Email
            </Label>
            <Input
              id="email"
              type="email"
              placeholder="seu@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="border-[#DDDDDD] focus:border-[#6C69EB] focus:ring-[#6C69EB]"
            />
          </div>

          {mode === "register" && (
            <div className="space-y-2">
              <Label htmlFor="telefone" className="text-[#1a1a1a]">
                Telefone
              </Label>
              <Input
                id="telefone"
                placeholder="11 99999-9999"
                value={telefone}
                onChange={(e) => setTelefone(e.target.value)}
                required
                className="border-[#DDDDDD] focus:border-[#6C69EB] focus:ring-[#6C69EB]"
              />
            </div>
          )}

          <div className="space-y-2">
            <Label htmlFor="password" className="text-[#1a1a1a]">
              Senha
            </Label>
            <Input
              id="password"
              type="password"
              placeholder="Digite sua senha"
              value={mode === "login" ? password : registerPassword}
              onChange={(e) => (mode === "login" ? setPassword(e.target.value) : setRegisterPassword(e.target.value))}
              required
              className="border-[#DDDDDD] focus:border-[#6C69EB] focus:ring-[#6C69EB]"
            />
          </div>

          <Button type="submit" disabled={loading} className="w-full bg-[#6C69EB] text-white hover:bg-[#5b5ad4]">
            {loading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
            {mode === "login" ? "Entrar" : "Criar Conta"}
          </Button>

          <div className="text-center text-sm text-gray-600">
            {mode === "login" ? (
              <>
                Não tem conta?{" "}
                <button
                  type="button"
                  onClick={() => setMode("register")}
                  className="text-[#6C69EB] hover:underline font-semibold"
                >
                  Cadastre-se
                </button>
              </>
            ) : (
              <>
                Já tem conta?{" "}
                <button
                  type="button"
                  onClick={() => setMode("login")}
                  className="text-[#6C69EB] hover:underline font-semibold"
                >
                  Entrar
                </button>
              </>
            )}
          </div>
        </form>
      </DialogContent>
    </Dialog>
  )
}
