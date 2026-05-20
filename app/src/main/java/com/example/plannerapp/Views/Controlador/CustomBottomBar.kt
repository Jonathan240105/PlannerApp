package com.example.plannerapp.Views.Controlador

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.plannerapp.ui.theme.azulPrimario

@Composable
fun CustomBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = rutaActual == "Principal",
            onClick = {
                if (rutaActual != "Principal") navController.navigate("Principal")
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "Principal",
                    tint = if (rutaActual == "Principal") azulPrimario else Color.Gray.copy(alpha = 0.6f)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = azulPrimario.copy(alpha = 0.1f)
            )
        )

        NavigationBarItem(
            selected = rutaActual == "Perfil",
            onClick = {
                if (rutaActual != "Perfil") navController.navigate("Perfil")
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Perfil",
                    tint = if (rutaActual == "Perfil") azulPrimario else Color.Gray.copy(alpha = 0.6f)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = azulPrimario.copy(alpha = 0.1f)
            )
        )
    }
}