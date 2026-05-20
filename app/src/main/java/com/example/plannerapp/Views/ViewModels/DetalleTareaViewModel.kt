package com.example.plannerapp.Views.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plannerapp.Data.RemoteData.Responses.toSubtarea
import com.example.plannerapp.Data.Repository.Repository
import com.example.plannerapp.Domain.ModelDetalleTarea
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class DetalleTareaViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _model = MutableStateFlow(ModelDetalleTarea())
    val model = _model.asStateFlow()

    fun cargarSubtareas(idTarea: Int) {
        viewModelScope.launch {
            _model.update { it.copy(cargandoSubtareas = true) }
            try {
                val listaSubtareas = repository.obtenerSubtareas(idTarea)

                _model.update {
                    it.copy(
                        listaSubtareas = listaSubtareas.map { it.toSubtarea() },
                        exitoSubtareas = true,
                        cargandoSubtareas = false
                    )
                }

            } catch (e: Exception) {
                println("Algo fue mal ${e.message}")
                _model.update { it.copy(listaSubtareas = emptyList()) }
            }
        }
    }

    fun cargarTarea(idTarea: Int) {
        viewModelScope.launch {
            _model.update { it.copy(cargandoTarea = true) }

            try {
                val tarea = repository.getTarea(idTarea)
                _model.update { it.copy(tarea = tarea, exitoTarea = true, cargandoTarea = false) }
            } catch (e: Exception) {
                println("Algo fue mal ${e.message}")
                _model.update { it.copy(exitoTarea = false, cargandoTarea = false) }
            }
        }
    }
}