package com.example.plannerapp.Data.LocalData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.plannerapp.Data.LocalData.Tareas.TareaEntity
import com.example.plannerapp.Data.LocalData.Tareas.TareaLocalDao

@Database(entities = [TareaEntity::class], version = 0, exportSchema = false)
abstract class BaseDeDatos : RoomDatabase() {
    abstract fun TareaLocalDao(): TareaLocalDao
}