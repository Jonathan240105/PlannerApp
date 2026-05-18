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
import kotlinx.coroutines.flow.combine

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
        }
    ) { padding ->
        val inicioViewModel: InicioSesionViewModel = hiltViewModel()
        NavHost(navController = controller, startDestination = "InicioSesion") {

            composable("InicioSesion") {
                PantallaInicioSesion(padding, inicioViewModel, { controller.navigate("Principal") })
            }
            composable("Principal") {
                PantallaPrincipal(
                    paddingValues = padding,
                    listasConTareas = listOf(
                        ListaConTareas(
                            1,
                            "Lista1",
                            listOf(
                                Tarea(
                                    1,
                                    "Tarea1"
                                ),
                                Tarea(
                                    1,
                                    "Tarea1"
                                ),
                                Tarea(
                                    1,
                                    "Tarea1"
                                ),
                                Tarea(
                                    1,
                                    "Tarea1"
                                ),
                                Tarea(
                                    1,
                                    "Tarea1"
                                ),
                            ),
                            1
                        ),
                        ListaConTareas(
                            1,
                            "Lista1",
                            listOf(
                                Tarea(
                                    1,
                                    "Tarea1"
                                ),
                                Tarea(
                                    1,
                                    "Tarea1"
                                ),
                                Tarea(
                                    1,
                                    "Tarea1"
                                ),
                                Tarea(
                                    1,
                                    "Tarea1"
                                ),
                                Tarea(
                                    1,
                                    "Tarea1"
                                ),
                            ),
                            1
                        ),
                    ),
                    onTareaClick = {},
                    onNuevaTareaClick = {}
                )
            }
        }
    }
}