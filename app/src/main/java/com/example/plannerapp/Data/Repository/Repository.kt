package com.example.plannerapp.Data.Repository

import com.example.plannerapp.Data.RemoteData.Responses.CrearTareaSolicitud
import com.example.plannerapp.Data.RemoteData.Responses.ListaMiembrosRespuesta
import com.example.plannerapp.Data.RemoteData.Responses.SubtareaRespuesta
import com.example.plannerapp.Data.RemoteData.Responses.UsuarioPerfilRespuesta
import com.example.plannerapp.Domain.EstadoInicioSesion
import com.example.plannerapp.Domain.ListaConTareas
import com.example.plannerapp.Domain.Subtarea
import com.example.plannerapp.Domain.TareaDomain
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun iniciarSesion(email: String, contra: String): Flow<EstadoInicioSesion>
    suspend fun obtenerListas(): List<ListaConTareas>
    suspend fun crearLista(nombre: String): Boolean
    suspend fun crearTarea(body: CrearTareaSolicitud, idAsignado: Int, idLista: Int): Boolean
    suspend fun obtenerListasEquipo(): List<ListaConTareas>
    suspend fun obtenerMiembrosEquipo(): List<ListaMiembrosRespuesta>
    suspend fun obtenerPerfilUsuario(): UsuarioPerfilRespuesta?
    suspend fun cerrarSesion()
    suspend fun obtenerSubtareas(idTarea: Int): List<SubtareaRespuesta>
    suspend fun getTarea(idTarea: Int): TareaDomain
    suspend fun cambiarEstadoSubtarea(idSubtarea: Int): Subtarea
}