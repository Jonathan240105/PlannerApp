package com.example.plannerapp.Data.LocalData.Tareas

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plannerapp.Data.RemoteData.Responses.TareaCortaRespuesta
import com.example.plannerapp.Domain.Tarea

@Entity(tableName = "Tareas")
data class TareaEntity(
    @PrimaryKey
    val idtarea: Int,
    val titulo: String,
    val fecha_limite: String?,
    val idListaAsignada: Int?,
    val fecha_creacion: String?,
    val idCreador: Int?
)

fun TareaEntity.toTarea(): Tarea {
    return Tarea(
        idTarea = idtarea,
        titulo = titulo,
        fecha_limite = fecha_limite ?: "",
        idListaAsignada = idListaAsignada ?: 0,
        fecha_creacion = fecha_creacion ?: "",
        idCreador = idCreador ?: 0
    )
}

fun TareaCortaRespuesta.toEntity(): TareaEntity {
    return TareaEntity(
        idtarea = idTarea,
        titulo = titulo,
        fecha_limite = null,
        idListaAsignada = null,
        fecha_creacion = null,
        idCreador = null
    )
}