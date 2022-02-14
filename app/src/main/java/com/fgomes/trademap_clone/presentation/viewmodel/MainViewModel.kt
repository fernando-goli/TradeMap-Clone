package com.fgomes.trademap_clone.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.fgomes.trademap_clone.data.ConectorMqtt
import com.fgomes.trademap_clone.repository.AcaoRepository

class MainViewModel(private val acaoRepository: AcaoRepository) : ViewModel() {

    private val conectorMqtt = ConectorMqtt()

    fun consumirAcoes() {
        conectorMqtt.start { acao ->
            acaoRepository.salvar(acao)
        }
    }

    fun pararCosumirAcoes() {
        conectorMqtt.desligar()
    }
}