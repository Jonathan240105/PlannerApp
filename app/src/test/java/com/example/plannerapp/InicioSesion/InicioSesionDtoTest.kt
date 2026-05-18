package com.example.plannerapp

import com.example.plannerapp.Data.RemoteData.Responses.InicioSesionRespuesta
import com.example.plannerapp.Data.RemoteData.Responses.InicioSesionSolicitud
import com.example.plannerapp.Data.RemoteData.Responses.UsuarioRespuesta
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ModelosInicioSesionTest {


    @Test
    fun usuarioRespuesta_creacionExitosa() {
        val usuario =
            UsuarioRespuesta(1, "email", "erik", "url")

        assertEquals(1, usuario.id)
        assertEquals("email", usuario.email)
        assertEquals("erik", usuario.nombre)
        assertEquals("url", usuario.foto)
    }

    @Test
    fun usuarioRespuesta_actualizarConCopy() {
        val usuarioOriginal =
            UsuarioRespuesta(1, "email", "erik", "url")

        val usuarioModificado = usuarioOriginal.copy(nombre = "Juan", foto = "nuevaFoto")

        assertEquals(1, usuarioModificado.id)
        assertEquals("email", usuarioModificado.email)
        assertEquals("Juan", usuarioModificado.nombre)
        assertEquals("nuevaFoto", usuarioModificado.foto)
    }

    @Test
    fun usuarioRespuesta_comprobarDuplicados() {
        val usuario1 =
            UsuarioRespuesta(1, "email", "Juan", "url")
        val usuario2 =
            UsuarioRespuesta(1, "email", "Juan", "url")
        val usuarioDiferente =
            UsuarioRespuesta(2, "email", "Juan", "url")

        assertEquals(usuario1, usuario2)
        assertNotEquals(usuario1, usuarioDiferente)
    }


    @Test
    fun inicioSesionRespuesta_creacionExitosa() {
        val usuario =
            UsuarioRespuesta(1, "email", "erik", "url")
        val respuesta = InicioSesionRespuesta(usuario, "token")

        assertEquals(usuario, respuesta.usuario)
        assertEquals("token", respuesta.token)
    }

    @Test
    fun inicioSesionRespuesta_actualizarConCopy() {
        val usuario =
            UsuarioRespuesta(1, "email", "erik", "url")
        val respuestaOriginal = InicioSesionRespuesta(usuario = usuario, token = "token")

        val respuestaModificada = respuestaOriginal.copy(token = "nuevoToken")

        assertEquals(usuario, respuestaModificada.usuario)
        assertEquals("nuevoToken", respuestaModificada.token)
    }

    @Test
    fun inicioSesionRespuesta_comprobarDuplicados() {
        val usuario1 =
            UsuarioRespuesta(1, "email", "erik", "url")
        val usuario2 =
            UsuarioRespuesta(1, "email", "erik", "url")

        val respuesta1 = InicioSesionRespuesta(usuario1, "token_abc")
        val respuesta2 = InicioSesionRespuesta(usuario2, "token_abc")

        assertEquals(respuesta1, respuesta2)
    }


    @Test
    fun inicioSesionSolicitud_creacionExitosa() {
        val solicitud = InicioSesionSolicitud(email = "email", contra = "123456")

        assertEquals("email", solicitud.email)
        assertEquals("123456", solicitud.contra)
    }

    @Test
    fun inicioSesionSolicitud_actualizarConCopy() {
        val solicitudOriginal = InicioSesionSolicitud(email = "email", contra = "123456")
        val solicitudModificada = solicitudOriginal.copy(contra = "nuevaContra")

        assertEquals("email", solicitudModificada.email)
        assertEquals("nuevaContra", solicitudModificada.contra)
    }

    @Test
    fun inicioSesionSolicitud_comprobarDuplicados() {
        val solicitud1 = InicioSesionSolicitud("email", "contra")
        val solicitud2 = InicioSesionSolicitud("email", "contra")

        assertEquals(solicitud1, solicitud2)
    }
}