package com.example.plannerapp.Views.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plannerapp.Data.RemoteData.Responses.CrearTareaSolicitud
import com.example.plannerapp.Data.Repository.Repository
import com.example.plannerapp.Domain.ModelPrincipal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrincipalViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _model = MutableStateFlow(ModelPrincipal())
    val model = _model.asStateFlow()

    fun getListasConTareas() {
        viewModelScope.launch {
            _model.update { it.copy(cargandoLista = true) }
            _model.update {
                it.copy(
                    listas = repository.obtenerListas(),
                    cargandoLista = false,
                    exitoLista = true
                )
            }
        }
    }

    private suspend fun refrescarPantallaActiva() {
        if (_model.value.vistasEquipo) {
            val listasEq = repository.obtenerListasEquipo()
            _model.update { it.copy(listasEquipo = listasEq) }
        } else {
            val misListas = repository.obtenerListas()
            _model.update { it.copy(listas = misListas) }
        }
    }

    fun crearLista(nombre: String) {
        viewModelScope.launch {
            _model.update { it.copy(cargandoLista = true) }
            val respuesta = repository.crearLista(nombre)

            if (respuesta) {
                refrescarPantallaActiva()
                _model.update { it.copy(cargandoLista = false, exitoLista = true) }
            } else {
                _model.update { it.copy(cargandoLista = false, exitoLista = false) }
            }
        }
    }

    fun crearTarea(body: CrearTareaSolicitud, idAsignado: Int, idLista: Int) {
        viewModelScope.launch {
            _model.update { it.copy(cargandoLista = true) }
            val respuesta = repository.crearTarea(body, idAsignado, idLista)

            if (respuesta) {
                refrescarPantallaActiva()
                _model.update { it.copy(cargandoLista = false, exitoLista = true) }
            } else {
                _model.update { it.copy(cargandoLista = false, exitoLista = false) }
            }
        }
    }

    fun cambiarVista(viendoEquipo: Boolean) {
        _model.update { it.copy(vistasEquipo = viendoEquipo) }
    }

    fun cargarDatosEquipo() {
        viewModelScope.launch {
            val listasEq = repository.obtenerListasEquipo()
            val miembros = repository.obtenerMiembrosEquipo()
            _model.update { it.copy(listasEquipo = listasEq, miembrosEquipo = miembros) }
        }
    }
}