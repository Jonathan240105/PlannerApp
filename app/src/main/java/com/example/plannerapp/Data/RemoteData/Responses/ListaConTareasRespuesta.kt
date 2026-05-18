package com.example.plannerapp.Data.RemoteData.Responses

data class TareaCortaRespuesta(
    val idTarea: Int, val titulo: String
)

data class ListaConTareasRespuesta(
    val idLista: Int,
    val nombreLista: String,
    val tareas: List<TareaCortaRespuesta>,
    val posicion: Int
)