package com.example.plannerapp.Data.RemoteData

import com.example.plannerapp.Data.RemoteData.Responses.CrearTareaSolicitud
import com.example.plannerapp.Data.RemoteData.Responses.InicioSesionRespuesta
import com.example.plannerapp.Data.RemoteData.Responses.InicioSesionSolicitud
import com.example.plannerapp.Data.RemoteData.Responses.ListaConTareasRespuesta
import com.example.plannerapp.Data.RemoteData.Responses.ListaMiembrosRespuesta
import com.example.plannerapp.Data.RemoteData.Variables.Endpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface DataInterface {
    @POST(Endpoints.iniciarSesion)
    suspend fun iniciarSesion(
        @Body body: InicioSesionSolicitud
    ): Response<InicioSesionRespuesta>

    @GET(Endpoints.obtenerListasConTareas)
    suspend fun obtenerListasConTareas(): Response<List<ListaConTareasRespuesta>>

    @POST(Endpoints.crearLista)
    suspend fun crearLista(
        @Query("nombre") nombreLista: String
    ): Response<okhttp3.ResponseBody>

    @POST(Endpoints.crearTarea)
    suspend fun crearTarea(
        @Body body: CrearTareaSolicitud,
        @Query("usuario") idAsignado: Int,
        @Query("lista") idLista: Int
    ): Response<okhttp3.ResponseBody>

    @GET(Endpoints.listarListasEquipo)
    suspend fun obtenerListasEquipoConTareas(): Response<List<ListaConTareasRespuesta>>

    @GET(Endpoints.listarMiembros)
    suspend fun obtenerMiembrosDropdown(): Response<List<ListaMiembrosRespuesta>>
}