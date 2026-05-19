package com.example.plannerapp.Views.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun crearLista(nombre: String) {
        viewModelScope.launch {
            _model.update { it.copy(cargandoLista = true) }
            val respuesta = repository.crearLista(nombre)

            if (respuesta) {
                _model.update {
                    it.copy(
                        listas = repository.obtenerListas(),
                        cargandoLista = false,
                        exitoLista = true
                    )
                }
            } else {
                _model.update {
                    it.copy(
                        cargandoLista = false,
                        exitoLista = false
                    )
                }
            }
            _model.update {
                it.copy()
            }
        }
    }
}