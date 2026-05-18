package com.example.plannerapp.Data.LocalData.Tareas

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TareaLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTarea(tarea: TareaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTareas(tareas: List<TareaEntity>)

    @Query("SELECT * FROM Tareas")
    suspend fun obtenerTareas(): List<TareaEntity>
}