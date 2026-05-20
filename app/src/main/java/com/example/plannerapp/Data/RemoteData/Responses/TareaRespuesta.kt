package com.example.plannerapp.Data.RemoteData.Responses

import com.example.plannerapp.Domain.TareaDomain

data class TareaRespuesta(
    val titulo: String,
    val descripcion: String,
    val nombreLista: String,
    val asignadoPor: String,
    val fechaLimite: String
)

fun TareaRespuesta.toTareaDomain() = TareaDomain(
    titulo = titulo,
    descripcion = descripcion,
    nombreLista = nombreLista,
    asignadoPor = asignadoPor,
    fechaLimite = fechaLimite
)
