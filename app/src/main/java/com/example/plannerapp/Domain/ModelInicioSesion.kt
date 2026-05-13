package com.example.plannerapp.Domain

data class ModelInicioSesion(
    val exito: Boolean = false,
    val cargando: Boolean = false,
    val email: String = "",
    val contra: String = ""
)
