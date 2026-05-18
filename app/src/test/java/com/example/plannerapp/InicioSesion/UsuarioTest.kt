package com.example.plannerapp.Domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class UsuarioTest {

    @Test
    fun usuario_creacionExitosa() {
        val usuario = Usuario(
            42, "email", "carlos", "url"
        )

        assertEquals(42, usuario.id)
        assertEquals("email", usuario.email)
        assertEquals("carlos", usuario.nombre)
        assertEquals("url", usuario.foto)
    }

    @Test
    fun usuario_actualizarConCopy() {
        val usuarioOriginal = Usuario(
            id = 42, email = "email", nombre = "carlos", foto = "url"
        )

        val usuarioModificado = usuarioOriginal.copy(nombre = "juan", foto = "urlNuevo")

        assertEquals(42, usuarioModificado.id)
        assertEquals("email", usuarioModificado.email)
        assertEquals("juan", usuarioModificado.nombre)
        assertEquals("urlNuevo", usuarioModificado.foto)
    }

    @Test
    fun usuario_comprobarDuplicados() {
        val usuario1 = Usuario(10, "email", "Ana", "ana.jpg")
        val usuario2 = Usuario(10, "email", "Ana", "ana.jpg")
        val usuarioDiferente = Usuario(11, "emailNuevo", "Ana", "ana.jpg")

        assertEquals(usuario1, usuario2)
        assertNotEquals(usuario1, usuarioDiferente)
    }
}