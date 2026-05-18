package com.example.plannerapp.Data.Repository

import com.example.plannerapp.Domain.EstadoInicioSesion
import com.example.plannerapp.Domain.ListaConTareas
import com.example.plannerapp.Domain.Tarea
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun iniciarSesion(email: String, contra: String): Flow<EstadoInicioSesion>
    suspend fun obtenerListas(): List<ListaConTareas>
}