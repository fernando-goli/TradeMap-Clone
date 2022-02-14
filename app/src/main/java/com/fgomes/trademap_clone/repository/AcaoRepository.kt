package com.fgomes.trademap_clone.repository

import com.fgomes.trademap_clone.domain.*

class AcaoRepository(private val acaoDAO: AcaoDao, private val apiService: ApiService) {

    fun salvar(acao: Acao) = acaoDAO.save(acao)

    fun getUltimo(codigo: String?) = if (codigo == null) {
        acaoDAO.getUltimo()
    } else {
        acaoDAO.getUltimo(codigo)
    }

    suspend fun getTodos(codigo: String) = acaoDAO.getTodos(codigo)

    suspend fun adicionarFavorito(codigo: String): Acao {
        val login = UsuarioLogado.usuario.login
        return apiService.adicionarFavorito(AcaoFavorita(login, codigo))
    }

}