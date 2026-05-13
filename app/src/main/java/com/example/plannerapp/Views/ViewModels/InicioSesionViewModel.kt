package com.example.plannerapp.Views.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plannerapp.Data.Repository.Repository
import com.example.plannerapp.Domain.ModelInicioSesion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InicioSesionViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _model = MutableStateFlow(ModelInicioSesion())
    val model = _model.asStateFlow()

    suspend fun iniciarSesion(email: String, contra: String) {
        _model.value = _model.value.copy(cargando = true)

        repository.iniciarSesion(email, contra).collect { resultado ->
            if (resultado.exito) {
                _model.update { it.copy(exito = true, cargando = false) }
            } else {
                _model.update { it.copy(exito = false, cargando = false) }
            }

        }

    }

    fun cambiarEmail(email: String) {
        viewModelScope.launch {
            _model.update { it.copy(email = email) }
        }
    }

    fun cambiarContra(contra: String) {
        viewModelScope.launch {
            _model.update { it.copy(contra = contra) }
        }
    }

    fun resetearModel() {
        _model.update { ModelInicioSesion() }
    }
}