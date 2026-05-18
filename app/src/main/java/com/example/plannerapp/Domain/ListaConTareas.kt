package com.example.plannerapp.Domain

data class Tarea(
    val idTarea: Int,
    val titulo: String,
    val fecha_limite: String = "",
    val idListaAsignada: Int = 0,
    val fecha_creacion: String = "",
    val idCreador: Int = 0
)

data class ListaConTareas(
    val idLista: Int,
    val nombreLista: String,
    val listaTareas: List<Tarea>,
    val posicion: Int
)