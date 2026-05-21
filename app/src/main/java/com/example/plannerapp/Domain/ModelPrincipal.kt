package com.example.plannerapp.Domain

import com.example.plannerapp.Data.RemoteData.Responses.ListaMiembrosRespuesta

data class ModelPrincipal(
    val exitoLista: Boolean? = null,
    val cargandoLista: Boolean? = null,
    val listas: List<ListaConTareas> = emptyList(),
    val listasEquipo: List<ListaConTareas> = emptyList(),
    val miembrosEquipo: List<ListaMiembrosRespuesta> = emptyList(),
    val esAdmin: Boolean = false,
    val vistasEquipo: Boolean = false
)