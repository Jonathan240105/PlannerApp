package com.example.plannerapp.Data.RemoteData.Responses

data class UsuarioPerfilRespuesta(
    val id: Int,
    val nombre: String,
    val email: String,
    val foto: String?,
    val rol: String
)