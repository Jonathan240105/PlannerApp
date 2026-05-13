package com.example.plannerapp.Data.Repository

import com.example.plannerapp.Domain.EstadoInicioSesion
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun iniciarSesion(email: String, contra: String): Flow<EstadoInicioSesion>
}