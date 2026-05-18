package com.example.plannerapp.Data.RemoteData.Responses

import com.example.plannerapp.Domain.Usuario

data class InicioSesionRespuesta(
    val usuario: UsuarioRespuesta,
    val token: String
)

data class UsuarioRespuesta(
    val id: Int,
    val email: String,
    val nombre: String,
    val foto: String
)

data class InicioSesionSolicitud(
    val email: String,
    val contra: String
)