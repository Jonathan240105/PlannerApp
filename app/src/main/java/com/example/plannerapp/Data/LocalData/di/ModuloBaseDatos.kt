package com.example.plannerapp.Data.LocalData.di

import android.content.Context
import androidx.room.Room
import com.example.plannerapp.Data.LocalData.BaseDeDatos
import com.example.plannerapp.Data.LocalData.Tareas.TareaLocalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloBaseDatos {

    @Provides
    @Singleton
    fun creadorBaseDeDatos(@ApplicationContext contexto: Context): BaseDeDatos {
        return Room.databaseBuilder(
            contexto, BaseDeDatos::class.java, "Base de datos tickly"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun TareaLocalDao(baseDeDatos: BaseDeDatos): TareaLocalDao {
        return baseDeDatos.TareaLocalDao()
    }
}