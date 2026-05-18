package com.example.plannerapp.Data.LocalData

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class InterceptorAutenticacion @Inject constructor(
    private val manejadorDeSesiones: ManejadorDeSesiones
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val peticionOriginal = chain.request()
        val token = runBlocking { manejadorDeSesiones.tokenFlow.first() }
        val nuevaPeticion = if (!token.isNullOrBlank()) {
            peticionOriginal.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            peticionOriginal
        }
        return chain.proceed(nuevaPeticion)

    }
}