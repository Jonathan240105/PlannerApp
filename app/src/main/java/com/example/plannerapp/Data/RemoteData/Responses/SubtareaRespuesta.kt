package com.example.plannerapp.Data.RemoteData.Responses

import com.example.plannerapp.Domain.Subtarea

data class SubtareaRespuesta(
    val id: Int = 0,
    val titulo: String = "",
    val tarea: String = "",
    val estado: Boolean = false
)

fun SubtareaRespuesta.toSubtarea() = Subtarea(
    id = id,
    titulo = titulo,
    tarea = tarea,
    estado = estado
)