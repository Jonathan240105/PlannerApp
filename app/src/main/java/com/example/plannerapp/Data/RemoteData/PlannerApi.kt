package com.example.plannerapp.Data.RemoteData

import com.example.plannerapp.Data.LocalData.InterceptorAutenticacion
import com.example.plannerapp.Data.RemoteData.Variables.APIUrls.urlPlanner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlannerApi {

    @Provides
    @Singleton
    fun provideOkHttpClient(InterceptorAutenticacion: InterceptorAutenticacion): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(
            InterceptorAutenticacion
        ).build()

    @Provides
    @Singleton
    fun provideRetrofit(cliente: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(urlPlanner)
            .client(cliente)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideDataInterface(retrofit: Retrofit): DataInterface =
        retrofit.create(DataInterface::class.java)

}