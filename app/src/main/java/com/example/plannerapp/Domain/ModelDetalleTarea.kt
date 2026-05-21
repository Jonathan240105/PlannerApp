package com.example.plannerapp.Domain

data class ModelDetalleTarea(
    val tarea: TareaDomain = TareaDomain(),
    val listaSubtareas: List<Subtarea> = emptyList(),
    val exitoSubtareas : Boolean = false,
    val cargandoSubtareas : Boolean = false,
    val exitoTarea : Boolean = false,
    val cargandoTarea : Boolean = false
)
