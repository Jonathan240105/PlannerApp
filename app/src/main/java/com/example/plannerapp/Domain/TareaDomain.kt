package com.example.plannerapp.Domain

data class TareaDomain(
    val titulo: String = "",
    val descripcion: String = "",
    val nombreLista: String = "",
    val asignadoPor: String = "",
    val fechaLimite: String = ""
)
