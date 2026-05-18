package com.example.plannerapp.Domain

data class TareaCorta(
    val idTarea: Int, val titulo: String
)

data class ListaConTareas(
    val idLista: Int,
    val nombreLista: String,
    val tareas: List<TareaCorta>,
    val posicion: Int
)