package com.example.plannerapp.Data.RemoteData.Responses

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ListaTareasRespuestaTest {

    @Test
    fun Creacion_TareaCortaRespuesta_y_ListaConTareasRespuesta() {
        val tarea = TareaCortaRespuesta(idTarea = 1, titulo = "To-DO")
        val lista = ListaConTareasRespuesta(
            idLista = 10,
            nombreLista = "Doing",
            tareas = listOf(tarea),
            posicion = 1
        )

        assertEquals(1, tarea.idTarea)
        assertEquals("To-DO", tarea.titulo)

        assertEquals(10, lista.idLista)
        assertEquals("Doing", lista.nombreLista)
        assertEquals(1, lista.tareas.size)
        assertEquals("To-DO", lista.tareas[0].titulo)
        assertEquals(1, lista.posicion)
    }

    @Test
    fun Actualizacion_ListaConTareasRespuesta() {
        val listaOriginal = ListaConTareasRespuesta(
            idLista = 5,
            nombreLista = "En progreso",
            tareas = emptyList(),
            posicion = 2
        )

        val listaModificada = listaOriginal.copy(nombreLista = "Terminado")

        assertEquals("Terminado", listaModificada.nombreLista)
        assertEquals(5, listaModificada.idLista)
        assertEquals(2, listaModificada.posicion)
        assertTrue(listaModificada.tareas.isEmpty())
    }

    @Test
    fun `Dos objetos con los mismos valores son un duplicado`() {
        val tarea1 = TareaCortaRespuesta(idTarea = 3, titulo = "titulo")

        val tareaClonada = tarea1.copy()

        assertEquals(tarea1, tareaClonada)
        assertEquals(tarea1.hashCode(), tareaClonada.hashCode())
    }

    @Test
    fun `dos objetos con distintos datos no son iguales`() {
        val tareaA = TareaCortaRespuesta(idTarea = 1, titulo = "Tarea A")
        val tareaB = TareaCortaRespuesta(idTarea = 2, titulo = "Tarea B")

        assertNotEquals(tareaA, tareaB)
    }
}