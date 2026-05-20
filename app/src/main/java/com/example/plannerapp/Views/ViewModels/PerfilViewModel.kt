package com.example.plannerapp.Views.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plannerapp.Data.LocalData.SeguridadToken.ManejadorDeSesiones
import com.example.plannerapp.Data.Repository.Repository
import com.example.plannerapp.Domain.ModelPerfil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val repository: Repository,
    manejadorDeSesiones: ManejadorDeSesiones
) : ViewModel() {
    private val _model = MutableStateFlow(ModelPerfil())
    val model = _model.asStateFlow()


    fun cargarPerfilUsuario() {
        viewModelScope.launch {
            val perfil = repository.obtenerPerfilUsuario()
            if (perfil != null) {
                _model.update { it.copy(perfilUsuario =  perfil) }
            }
        }
    }

    fun cerrarSesion(){
        viewModelScope.launch {
            repository.cerrarSesion()
        }
    }
}