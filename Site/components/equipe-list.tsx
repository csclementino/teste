"use client"

import { useEffect, useState } from "react"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Alert, AlertDescription } from "@/components/ui/alert"
import { Edit2, Trash2, AlertCircle, Loader2, Users } from "lucide-react"
import EquipeForm from "./equipe-form"

interface Equipe {
  id: string
  nome: string
  descricao: string
}

interface EquipeListProps {
  token: string
  refreshTrigger: number
  onRefresh: () => void
}

export default function EquipeList({ token, refreshTrigger, onRefresh }: EquipeListProps) {
  const [equipes, setEquipes] = useState<Equipe[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState("")
  const [editingId, setEditingId] = useState<string | null>(null)
  const [deleteConfirm, setDeleteConfirm] = useState<string | null>(null)
  const [deleting, setDeleting] = useState(false)

  const fetchEquipes = async () => {
    setLoading(true)
    setError("")
    try {
    const response = await fetch("http://localhost:8080/equipe", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      if (response.status === 204) {
        setEquipes([]);
        return;
      }
      throw new Error("Erro ao carregar equipes");
    }

    const text = await response.text();
    const data = text ? JSON.parse(text) : [];

    setEquipes(Array.isArray(data) ? data : []);
  } catch (err) {
    setError(err instanceof Error ? err.message : "Erro ao carregar equipes");
    setEquipes([]);
  } finally {
    setLoading(false);
  }
  }

  useEffect(() => {
    fetchEquipes()
  }, [token, refreshTrigger])

  const handleDelete = async (id: string) => {
    setDeleting(true)
    try {
      const response = await fetch(`http://localhost:8080/equipe/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

      if (!response.ok) {
        throw new Error("Erro ao deletar equipe")
      }

      setDeleteConfirm(null)
      onRefresh()
    } catch (err) {
      setError(err instanceof Error ? err.message : "Erro ao deletar equipe")
    } finally {
      setDeleting(false)
    }
  }

  if (loading) {
    return (
      <div className="flex justify-center py-12">
        <Loader2 className="h-8 w-8 animate-spin text-[#6C69EB]" />
      </div>
    )
  }

  if (error && equipes.length === 0) {
    return (
      <Alert variant="destructive" className="border-red-300 bg-red-50">
        <AlertCircle className="h-4 w-4" />
        <AlertDescription>{error}</AlertDescription>
      </Alert>
    )
  }

  if (equipes.length === 0) {
    return (
      <Card className="border-[#DDDDDD] bg-white text-center py-12">
        <Users className="mx-auto h-12 w-12 text-[#C4C3F7] mb-4" />
        <p className="text-gray-600">Nenhuma equipe criada ainda.</p>
        <p className="text-sm text-gray-500">Clique em "Nova Equipe" para começar</p>
      </Card>
    )
  }

  return (
    <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
      {equipes.map((equipe) => (
        <Card key={equipe.id} className="border-[#DDDDDD] bg-white hover:shadow-lg transition-shadow">
          <CardHeader>
            <CardTitle className="text-[#6C69EB]">{equipe.nome}</CardTitle>
            <CardDescription className="line-clamp-2">{equipe.descricao}</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="flex gap-2">
              <Dialog open={editingId === equipe.id} onOpenChange={(open) => !open && setEditingId(null)}>
                <DialogTrigger asChild>
                  <Button
                    size="sm"
                    variant="outline"
                    onClick={() => setEditingId(equipe.id)}
                    className="flex-1 border-[#DDDDDD] text-[#6C69EB]"
                  >
                    <Edit2 className="mr-2 h-4 w-4" />
                    Editar
                  </Button>
                </DialogTrigger>
                <DialogContent className="border-[#DDDDDD] bg-white">
                  <DialogHeader>
                    <DialogTitle className="text-[#6C69EB]">Editar Equipe</DialogTitle>
                    <DialogDescription>Atualize as informações da equipe</DialogDescription>
                  </DialogHeader>
                  <EquipeForm
                    token={token}
                    equipeId={equipe.id}
                    initialData={equipe}
                    onSuccess={() => {
                      setEditingId(null)
                      onRefresh()
                    }}
                    onCancel={() => setEditingId(null)}
                  />
                </DialogContent>
              </Dialog>

              <Dialog open={deleteConfirm === equipe.id} onOpenChange={(open) => !open && setDeleteConfirm(null)}>
                <DialogTrigger asChild>
                  <Button
                    size="sm"
                    variant="outline"
                    onClick={() => setDeleteConfirm(equipe.id)}
                    className="flex-1 border-red-300 text-red-600 hover:bg-red-50"
                  >
                    <Trash2 className="mr-2 h-4 w-4" />
                    Deletar
                  </Button>
                </DialogTrigger>
                <DialogContent className="border-[#DDDDDD] bg-white">
                  <DialogHeader>
                    <DialogTitle className="text-red-600">Confirmar Exclusão</DialogTitle>
                    <DialogDescription>
                      Tem certeza que deseja deletar a equipe "{equipe.nome}"? Esta ação não pode ser desfeita.
                    </DialogDescription>
                  </DialogHeader>
                  <div className="flex gap-3">
                    <Button
                      onClick={() => handleDelete(equipe.id)}
                      disabled={deleting}
                      className="flex-1 bg-red-600 text-white hover:bg-red-700"
                    >
                      {deleting && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                      Deletar
                    </Button>
                    <Button
                      onClick={() => setDeleteConfirm(null)}
                      variant="outline"
                      className="flex-1 border-[#DDDDDD]"
                    >
                      Cancelar
                    </Button>
                  </div>
                </DialogContent>
              </Dialog>
            </div>
          </CardContent>
        </Card>
      ))}
    </div>
  )
}
