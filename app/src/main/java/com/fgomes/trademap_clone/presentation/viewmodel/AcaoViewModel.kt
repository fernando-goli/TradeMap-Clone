package com.fgomes.trademap_clone.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fgomes.trademap_clone.domain.Acao
import com.fgomes.trademap_clone.repository.AcaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AcaoViewModel(private val acaoRepository: AcaoRepository): ViewModel() {

    val acaoAdicionada = MutableLiveData<Acao>()

    fun getUltimo(codigo: String? = null) = acaoRepository.getUltimo(codigo)

    fun getTodos(codigo: String): MutableLiveData<List<Acao>> {
        val acoesLiveData = MutableLiveData<List<Acao>>()
        viewModelScope.launch(Dispatchers.IO) {
            val todos = acaoRepository.getTodos(codigo)
            acoesLiveData.postValue(todos)
        }
        return acoesLiveData
    }

    fun adicionarAcao(codigo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val acao = acaoRepository.adicionarFavorito(codigo)
                acaoAdicionada.postValue(acao)
            }.onFailure {
                Log.e("", it.message.orEmpty())
            }
        }
    }

}