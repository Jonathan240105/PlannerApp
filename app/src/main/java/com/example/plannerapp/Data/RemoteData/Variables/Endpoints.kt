package com.example.plannerapp.Data.RemoteData.Variables

object Endpoints {
    const val iniciarSesion = "/usuarios/iniciarSesion"
    const val obtenerListasConTareas = "/lista/nombreTareas"
    const val crearLista = "/lista/nuevo"
    const val crearTarea = "/tareas/nuevo"
    const val listarListasEquipo = "lista/equipo"
    const val listarMiembros = "tareas/equipo/miembros"

    const val obtenerPerfil = "usuarios/perfil"
    const val obtenerSubtareas = "/subtareas/{idSubtarea}"
    const val getTarea = "/tareas/get/{id}"
    const val cambiarEstadoSubtarea = "subtareas/{idSubtarea}/estado"
}