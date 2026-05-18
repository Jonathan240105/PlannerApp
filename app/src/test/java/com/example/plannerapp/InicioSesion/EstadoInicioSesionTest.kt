package com.example.plannerapp.InicioSesion

import com.example.plannerapp.Domain.EstadoInicioSesion
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Assert.assertNotEquals
import org.junit.Test

class EstadoInicioSesionTest {
    @Test
    fun estadoInicioSesion_creacionExitosa() {
        val estadoInicial = EstadoInicioSesion(true, null)
        val estadoConToken = EstadoInicioSesion(true, "token")

        assertEquals(true, estadoInicial.exito)
        assertNull(estadoInicial.token)

        assertEquals(true, estadoConToken.exito)
        assertEquals("token", estadoConToken.token)
    }

    @Test
    fun estadoInicioSesion_actualizarConCopy() {
        val estadoOriginal = EstadoInicioSesion(true, null)

        val estadoModificado = estadoOriginal.copy(exito = false)

        assertEquals(false, estadoModificado.exito)
        assertNull(estadoModificado.token)
    }

    @Test
    fun estadoInicioSesion_comprobarDuplicados() {
        val estado1 = EstadoInicioSesion(false, null)
        val estado2 = EstadoInicioSesion(false, null)
        val estadoDiferente = EstadoInicioSesion(exito = true, token = "token")

        assertEquals(estado1, estado2)
        assertNotEquals(estado1, estadoDiferente)
    }
}