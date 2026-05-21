package com.example.plannerapp.Domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ModelPrincipalTest {

    @Test
    fun `Creacion ModelPrincipal`() {
        val estadoInicial = ModelPrincipal()

        assertNull(estadoInicial.exitoLista)
        assertNull(estadoInicial.cargandoLista)
        assertTrue(estadoInicial.listas.isEmpty())
    }

    @Test
    fun `actualizacion ModelPrincipal`() {
        val estadoCargando = ModelPrincipal(cargandoLista = true)

        val listasMock = listOf(
            ListaConTareas(
                idLista = 1,
                nombreLista = "To Do",
                listaTareas = emptyList(),
                posicion = 1
            )
        )

        val estadoExito = estadoCargando.copy(
            cargandoLista = false,
            exitoLista = true,
            listas = listasMock
        )

        assertFalse(estadoExito.cargandoLista == true)
        assertTrue(estadoExito.exitoLista == true)
        assertEquals(1, estadoExito.listas.size)
        assertEquals("To Do", estadoExito.listas[0].nombreLista)
    }

    @Test
    fun `Dos objetos ModelPrincipal con los mismos valores son un duplicado`() {
        val estadoA = ModelPrincipal(exitoLista = false, cargandoLista = false)

        val estadoB = estadoA.copy()

        assertEquals(estadoA, estadoB)
        assertEquals(estadoA.hashCode(), estadoB.hashCode())
    }

}