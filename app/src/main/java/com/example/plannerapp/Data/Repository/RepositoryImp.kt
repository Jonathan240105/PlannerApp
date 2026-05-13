package com.example.plannerapp.Data.Repository

import com.example.plannerapp.Data.RemoteData.DataInterface
import com.example.plannerapp.Domain.EstadoInicioSesion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val dataInterface: DataInterface
) : Repository {

    override suspend fun iniciarSesion(
        email: String,
        contra: String
    ): Flow<EstadoInicioSesion> = flow {

        val respuesta = dataInterface.iniciarSesion(email, contra)
        if (respuesta.isSuccessful) {
            emit(EstadoInicioSesion(exito = true, respuesta.body()?.token))
        } else {
            emit(EstadoInicioSesion(exito = false, null))
        }
    }

}