package com.example.plannerapp.Domain

data class ModelInicioSesion(
    val exito: Boolean? = null,
    val cargando: Boolean? = null,
    val email: String = "",
    val contra: String = ""
)
