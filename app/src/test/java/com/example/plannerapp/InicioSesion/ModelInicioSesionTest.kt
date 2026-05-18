package com.example.plannerapp

import com.example.plannerapp.Domain.ModelInicioSesion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ModelInicioSesionTest {

    @Test
    fun modelInicioSesion_creacionExitosa() {

        val modeloEspecifico = ModelInicioSesion(
            true,
            false,
            "email",
            "contra"
        )

        assertEquals(true, modeloEspecifico.exito)
        assertEquals(false, modeloEspecifico.cargando)
        assertEquals("email", modeloEspecifico.email)
        assertEquals("contra", modeloEspecifico.contra)
    }

    @Test
    fun modelInicioSesion_actualizarConCopy() {
        val modeloOriginal = ModelInicioSesion(
            exito = null,
            cargando = false,
            email = "email",
            contra = "123"
        )

        val modeloModificado = modeloOriginal.copy(cargando = true)

        assertNull(modeloModificado.exito)
        assertEquals(true, modeloModificado.cargando)
        assertEquals("email", modeloModificado.email)
        assertEquals("123", modeloModificado.contra)
    }

    @Test
    fun modelInicioSesion_comprobarDuplicados() {
        val modelo1 = ModelInicioSesion(exito = false, cargando = false, email = "email")
        val modelo2 = ModelInicioSesion(exito = false, cargando = false, email = "email")
        val modeloDiferente =
            ModelInicioSesion(exito = true, cargando = false, email = "error@web.com")

        assertEquals(modelo1, modelo2)
        assertNotEquals(modelo1, modeloDiferente)
    }
}