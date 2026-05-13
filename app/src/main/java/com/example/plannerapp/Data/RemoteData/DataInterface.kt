package com.example.plannerapp.Data.RemoteData

import com.example.plannerapp.Data.RemoteData.Responses.InicioSesionRespuesta
import com.example.plannerapp.Data.RemoteData.Variables.Endpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET


interface DataInterface {
    @GET(Endpoints.iniciarSesion)
    suspend fun iniciarSesion(
        @Body email: String,
        @Body contra: String
    ): Response<InicioSesionRespuesta>
}