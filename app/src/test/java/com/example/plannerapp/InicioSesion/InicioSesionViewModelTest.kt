package com.example.plannerapp.Views.ViewModels

import com.example.plannerapp.Data.Repository.Repository
import com.example.plannerapp.Domain.EstadoInicioSesion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class InicioSesionViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val repository: Repository = mock()

    private lateinit var viewModel: InicioSesionViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = InicioSesionViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun estadoInicial_esPorDefecto() {
        val estadoActual = viewModel.model.value

        assertNull(estadoActual.exito)
        assertNull(estadoActual.cargando)
        assertEquals("", estadoActual.email)
        assertEquals("", estadoActual.contra)
    }

    @Test
    fun cambiarEmail_actualizaElEstadoCorrectamente() = runTest {
        viewModel.cambiarEmail("email")

        advanceUntilIdle()

        assertEquals("email", viewModel.model.value.email)
    }

    @Test
    fun cambiarContra_actualizaElEstadoCorrectamente() = runTest {
        viewModel.cambiarContra("abc123")

        advanceUntilIdle()

        assertEquals("abc123", viewModel.model.value.contra)
    }

    @Test
    fun iniciarSesion_cuandoEsExitoso_actualizaExitoYApagaCargando() = runTest {
        val email = "email"
        val contra = "123456"

        val resultadoFake = EstadoInicioSesion(exito = true, token = "token")
        whenever(repository.iniciarSesion(email, contra)).thenReturn(flowOf(resultadoFake))

        viewModel.iniciarSesion(email, contra)

        advanceUntilIdle()

        val estadoFinal = viewModel.model.value
        assertEquals(true, estadoFinal.exito)
        assertEquals(false, estadoFinal.cargando)
    }

    @Test
    fun iniciarSesion_cuandoFalla_actualizaExitoEnFalseYApagaCargando() = runTest {
        val email = "email"
        val contra = "contra"

        val resultadoFake = EstadoInicioSesion(false, null)
        whenever(repository.iniciarSesion(email, contra)).thenReturn(flowOf(resultadoFake))

        viewModel.iniciarSesion(email, contra)
        advanceUntilIdle()

        val estadoFinal = viewModel.model.value
        assertEquals(false, estadoFinal.exito)
        assertEquals(false, estadoFinal.cargando)
    }

    @Test
    fun resetearModel_devuelveElEstadoAValoresIniciales() = runTest {
        viewModel.cambiarEmail("email")
        advanceUntilIdle()

        viewModel.resetearModel()
        advanceUntilIdle()

        val estadoFinal = viewModel.model.value
        assertEquals("", estadoFinal.email)
        assertNull(estadoFinal.exito)
    }
}