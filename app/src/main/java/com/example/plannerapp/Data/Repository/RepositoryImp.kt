package com.example.plannerapp.Data.Repository

import com.example.plannerapp.Data.LocalData.SeguridadToken.ManejadorDeSesiones
import com.example.plannerapp.Data.LocalData.Tareas.TareaLocalDao
import com.example.plannerapp.Data.LocalData.Tareas.toEntity
import com.example.plannerapp.Data.LocalData.Tareas.toTarea
import com.example.plannerapp.Data.RemoteData.DataInterface
import com.example.plannerapp.Data.RemoteData.Responses.CrearTareaSolicitud
import com.example.plannerapp.Data.RemoteData.Responses.InicioSesionSolicitud
import com.example.plannerapp.Data.RemoteData.Responses.ListaConTareasRespuesta
import com.example.plannerapp.Data.RemoteData.Responses.ListaMiembrosRespuesta
import com.example.plannerapp.Data.RemoteData.Responses.UsuarioPerfilRespuesta
import com.example.plannerapp.Domain.EstadoInicioSesion
import com.example.plannerapp.Domain.ListaConTareas
import com.example.plannerapp.Domain.Tarea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val dataInterface: DataInterface,
    private val tareaLocalDao: TareaLocalDao,
    private val manejadorDeSesiones: ManejadorDeSesiones
) : Repository {

    override suspend fun iniciarSesion(
        email: String,
        contra: String
    ): Flow<EstadoInicioSesion> = flow {

        val respuesta = dataInterface.iniciarSesion(InicioSesionSolicitud(email, contra))
        if (respuesta.isSuccessful) {
            manejadorDeSesiones.guardarToken(respuesta.body()?.token ?: "")
            emit(EstadoInicioSesion(exito = true, respuesta.body()?.token))
        } else {
            emit(EstadoInicioSesion(exito = false, null))
        }
    }

    override suspend fun obtenerListas(): List<ListaConTareas> {
        return try {
            val respuesta = dataInterface.obtenerListasConTareas()

            if (respuesta.isSuccessful && respuesta.body() != null) {
                val listasDeRed = respuesta.body()!!

                listasDeRed.map { listaRespuesta ->
                    ListaConTareas(
                        idLista = listaRespuesta.idLista,
                        nombreLista = listaRespuesta.nombreLista,
                        posicion = listaRespuesta.posicion,
                        listaTareas = listaRespuesta.tareas.map { tareaRespuesta ->
                            Tarea(
                                idTarea = tareaRespuesta.idTarea,
                                titulo = tareaRespuesta.titulo
                            )
                        }
                    )
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            println(e.message)
            emptyList()
        }
    }

    override suspend fun crearLista(nombre: String): Boolean {
        try {
            val respuesta = dataInterface.crearLista(nombre)

            return respuesta.isSuccessful

        } catch (e: Exception) {
            println(e.message)
            return false
        }
    }

    override suspend fun crearTarea(
        body: CrearTareaSolicitud,
        idAsignado: Int,
        idLista: Int
    ): Boolean {
        try {
            val respuesta = dataInterface.crearTarea(body, idAsignado, idLista)

            return respuesta.isSuccessful

        } catch (e: Exception) {
            println(e.message)
            return false
        }
    }

    override suspend fun obtenerListasEquipo(): List<ListaConTareas> {
        return try {
            val respuesta = dataInterface.obtenerListasEquipoConTareas()
            if (respuesta.isSuccessful && respuesta.body() != null) {
                respuesta.body()!!.map { lista ->
                    ListaConTareas(
                        idLista = lista.idLista,
                        nombreLista = lista.nombreLista,
                        posicion = lista.posicion,
                        listaTareas = lista.tareas.map { Tarea(it.idTarea, it.titulo) }
                    )
                }
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun obtenerMiembrosEquipo(): List<ListaMiembrosRespuesta> {
        return try {
            val respuesta = dataInterface.obtenerMiembrosDropdown()
            if (respuesta.isSuccessful) respuesta.body() ?: emptyList() else emptyList()
        } catch (e: Exception) {
            println(
                "Error al obtener miembros del equipo: ${e.message}"
            )
            return emptyList()
        }
    }

    override suspend fun obtenerPerfilUsuario(): UsuarioPerfilRespuesta? {
        return try {
            val respuesta = dataInterface.obtenerPerfil()
            if (respuesta.isSuccessful) {
                return respuesta.body()
            } else {
                return null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun cerrarSesion() {
        manejadorDeSesiones.cerrarSesion()
    }


}