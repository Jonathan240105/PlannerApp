package com.example.plannerapp.Views.Controlador

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.plannerapp.Domain.ListaConTareas
import com.example.plannerapp.Domain.Tarea
import com.example.plannerapp.Views.Screens.PantallaInicioSesion
import com.example.plannerapp.Views.Screens.PantallaPrincipal
import com.example.plannerapp.Views.ViewModels.InicioSesionViewModel
import com.example.plannerapp.Views.ViewModels.PrincipalViewModel

@Composable
fun Controlador() {
    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    Scaffold(
        floatingActionButton = {
            if (rutaActual == "Principal") {
                IconoFlotante({})
            }
        },
        topBar = {
            if (rutaActual != "InicioSesion") {
                CustomTopBar(
                    when (rutaActual) {
                        "Principal" -> "Mi Workspace"
                        else -> ""
                    },
                    when (rutaActual) {
                        "Principal" -> "Espacio de trabajo y listado de tareas"
                        else -> ""
                    }
                )
            }
        }
    ) { padding ->
        val inicioViewModel: InicioSesionViewModel = hiltViewModel()
        val principalViewModel: PrincipalViewModel = hiltViewModel()

        NavHost(navController = controller, startDestination = "InicioSesion") {

            composable("InicioSesion") {
                PantallaInicioSesion(padding, inicioViewModel, { controller.navigate("Principal") })
            }
            composable("Principal") {
                PantallaPrincipal(
                    paddingValues = padding,
                    principalViewModel
                )
            }
        }
    }
}