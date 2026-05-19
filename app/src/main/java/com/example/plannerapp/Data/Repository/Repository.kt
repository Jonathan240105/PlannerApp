package com.example.plannerapp.Data.Repository

import com.example.plannerapp.Data.RemoteData.Responses.CrearTareaSolicitud
import com.example.plannerapp.Data.RemoteData.Responses.ListaMiembrosRespuesta
import com.example.plannerapp.Domain.EstadoInicioSesion
import com.example.plannerapp.Domain.ListaConTareas
import com.example.plannerapp.Domain.Tarea
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun iniciarSesion(email: String, contra: String): Flow<EstadoInicioSesion>
    suspend fun obtenerListas(): List<ListaConTareas>
    suspend fun crearLista(nombre: String): Boolean
    suspend fun crearTarea(body: CrearTareaSolicitud, idAsignado: Int, idLista: Int) : Boolean
    suspend fun obtenerListasEquipo(): List<ListaConTareas>
    suspend fun obtenerMiembrosEquipo(): List<ListaMiembrosRespuesta>
}