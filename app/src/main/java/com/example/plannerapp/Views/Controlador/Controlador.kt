package com.example.plannerapp.Views.Controlador

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plannerapp.Views.Screens.PantallaInicioSesion
import com.example.plannerapp.Views.ViewModels.InicioSesionViewModel
import kotlinx.coroutines.flow.combine

@Composable
fun Controlador() {
    val controller = rememberNavController()
    Scaffold() { padding ->
        val inicioViewModel: InicioSesionViewModel = hiltViewModel()
        NavHost(navController = controller, startDestination = "InicioSesion") {
            composable("InicioSesion") {
                PantallaInicioSesion(padding, inicioViewModel)
            }
        }
    }
}