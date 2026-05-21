package com.example.plannerapp.Domain

import org.junit.Assert.assertEquals
import org.junit.Test

class ListaTareasTest {

    @Test
    fun `Creacion de Tarea`() {
        val tarea = Tarea(
            idTarea = 42,
            titulo = "fichar"
        )

        assertEquals(42, tarea.idTarea)
        assertEquals("fichar", tarea.titulo)

        assertEquals("", tarea.fecha_limite)
        assertEquals(0, tarea.idListaAsignada)
        assertEquals("", tarea.fecha_creacion)
        assertEquals(0, tarea.idCreador)
    }

    @Test
    fun `Creacion de ListaConTareas `() {
        val tarea1 = Tarea(idTarea = 1, titulo = "titulo", idListaAsignada = 10)
        val tarea2 = Tarea(idTarea = 2, titulo = "revisar correo", idListaAsignada = 10)
        val listaTareasMock = listOf(tarea1, tarea2)

        val listaConTareas = ListaConTareas(
            idLista = 10,
            nombreLista = "to-do",
            listaTareas = listaTareasMock,
            posicion = 1
        )

        assertEquals(10, listaConTareas.idLista)
        assertEquals("to-do", listaConTareas.nombreLista)
        assertEquals(2, listaConTareas.listaTareas.size)
        assertEquals(1, listaConTareas.posicion)
        assertEquals("titulo", listaConTareas.listaTareas[0].titulo)
    }

    @Test
    fun `actualizacion Tarea`() {
        val tareaOriginal = Tarea(
            idTarea = 1,
            titulo = "Subir código",
            fecha_limite = "2026-05-20",
            idListaAsignada = 2,
            fecha_creacion = "2026-05-19",
            idCreador = 99
        )

        val tareaModificada = tareaOriginal.copy(
            idListaAsignada = 5,
            fecha_limite = "2026-05-25"
        )

        assertEquals(5, tareaModificada.idListaAsignada)
        assertEquals("2026-05-25", tareaModificada.fecha_limite)

        assertEquals(1, tareaModificada.idTarea)
        assertEquals("Subir código", tareaModificada.titulo)
        assertEquals("2026-05-19", tareaModificada.fecha_creacion)
        assertEquals(99, tareaModificada.idCreador)
    }

    @Test
    fun `Dos objetos ListaConTareas con los mismos valores es un duplicado`() {
        val listaOriginal = ListaConTareas(
            idLista = 3,
            nombreLista = "Doing",
            listaTareas = emptyList(),
            posicion = 4
        )

        val listaDuplicada = listaOriginal.copy()

        assertEquals(listaOriginal, listaDuplicada)
        assertEquals(listaOriginal.hashCode(), listaDuplicada.hashCode())
    }
}