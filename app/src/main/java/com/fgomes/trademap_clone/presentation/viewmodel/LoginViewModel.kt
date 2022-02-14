package com.fgomes.trademap_clone.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fgomes.trademap_clone.domain.ApiService
import com.fgomes.trademap_clone.domain.Usuario
import com.fgomes.trademap_clone.domain.UsuarioLogado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val apiService: ApiService) : ViewModel() {

    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = _usuario

    fun login(login: String){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val usuario = apiService.login(login)
                UsuarioLogado.usuario = usuario
                _usuario.postValue(usuario)
            }.onFailure {
                Log.i("", it.message.orEmpty())
            }
        }
    }
}