package com.example.plannerapp.Domain

data class ModelPrincipal(
    val exitoLista: Boolean? = null,
    val cargandoLista: Boolean? = null,
    val listas: List<ListaConTareas> = emptyList()
)