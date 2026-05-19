package com.example.plannerapp.Data.Repository

import com.example.plannerapp.Data.LocalData.SeguridadToken.ManejadorDeSesiones
import com.example.plannerapp.Data.LocalData.Tareas.TareaLocalDao
import com.example.plannerapp.Data.LocalData.Tareas.toEntity
import com.example.plannerapp.Data.LocalData.Tareas.toTarea
import com.example.plannerapp.Data.RemoteData.DataInterface
import com.example.plannerapp.Data.RemoteData.Responses.InicioSesionSolicitud
import com.example.plannerapp.Data.RemoteData.Responses.ListaConTareasRespuesta
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
}