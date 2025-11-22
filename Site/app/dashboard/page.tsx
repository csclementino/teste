"use client"

import { useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Alert, AlertDescription } from "@/components/ui/alert"
import { LogOut, Plus, AlertCircle } from "lucide-react"
import EquipeList from "@/components/equipe-list"
import EquipeForm from "@/components/equipe-form"

export default function Dashboard() {
  const router = useRouter()
  const [token, setToken] = useState<string | null>(null)
  const [showForm, setShowForm] = useState(false)
  const [refreshTrigger, setRefreshTrigger] = useState(0)
  const [error, setError] = useState("")

  useEffect(() => {
    const accessToken = localStorage.getItem("access_token")
    if (!accessToken) {
      router.push("/")
      return
    }
    setToken(accessToken)
  }, [router])

  const handleLogout = () => {
    localStorage.removeItem("access_token")
    router.push("/")
  }

  const handleEquipeCreated = () => {
    setShowForm(false)
    setRefreshTrigger((prev) => prev + 1)
  }

  if (!token) {
    return (
      <div className="flex min-h-screen items-center justify-center bg-[#F0F0FD]">
        <div className="text-center">
          <div className="animate-spin">
            <div className="h-8 w-8 border-4 border-[#6C69EB] border-t-transparent rounded-full" />
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-[#F0F0FD]">
      {/* Header */}
      <header className="border-b border-[#DDDDDD] bg-white">
        <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-3xl font-bold text-[#6C69EB]">Dashboard</h1>
              <p className="mt-1 text-sm text-gray-600">Gerencie suas equipes e monitore EPIs</p>
            </div>
            <Button
              onClick={handleLogout}
              variant="outline"
              className="border-[#DDDDDD] text-red-600 hover:bg-red-50 bg-transparent"
            >
              <LogOut className="mr-2 h-4 w-4" />
              Sair
            </Button>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
        {error && (
          <Alert variant="destructive" className="mb-6 border-red-300 bg-red-50">
            <AlertCircle className="h-4 w-4" />
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        )}

        {/* New Team Section */}
        {!showForm && (
          <div className="mb-8">
            <Button onClick={() => setShowForm(true)} className="bg-[#6C69EB] text-white hover:bg-[#5b5ad4]">
              <Plus className="mr-2 h-4 w-4" />
              Nova Equipe
            </Button>
          </div>
        )}

        {/* Form */}
        {showForm && (
          <Card className="mb-8 border-[#DDDDDD]">
            <CardHeader>
              <CardTitle className="text-[#6C69EB]">Criar Nova Equipe</CardTitle>
              <CardDescription>Preencha os dados para criar uma nova equipe</CardDescription>
            </CardHeader>
            <CardContent>
              <EquipeForm token={token} onSuccess={handleEquipeCreated} onCancel={() => setShowForm(false)} />
            </CardContent>
          </Card>
        )}

        {/* Teams List */}
        <div>
          <h2 className="mb-6 text-2xl font-bold text-[#1a1a1a]">Suas Equipes</h2>
          <EquipeList
            token={token}
            refreshTrigger={refreshTrigger}
            onRefresh={() => setRefreshTrigger((prev) => prev + 1)}
          />
        </div>
      </main>
    </div>
  )
}
