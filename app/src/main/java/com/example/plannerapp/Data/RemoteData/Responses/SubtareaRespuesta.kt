package com.example.plannerapp.Data.RemoteData.Responses

import com.example.plannerapp.Domain.Subtarea

data class SubtareaRespuesta(
    val titulo: String,
    val tarea: String,
    val estado: Boolean
)

fun SubtareaRespuesta.toSubtarea() = Subtarea(
    titulo = titulo,
    tarea = tarea,
    estado = estado
)