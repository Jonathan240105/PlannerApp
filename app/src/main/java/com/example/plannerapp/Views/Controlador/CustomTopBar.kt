package com.example.plannerapp.Views.Controlador

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plannerapp.ui.theme.Typography
import com.example.plannerapp.ui.theme.azulPrimario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(texto: String, descripcion: String) {
    TopAppBar(modifier = Modifier.height(110.dp),
        title = {
            Column(Modifier.padding(vertical = 10.dp)){
                Text(
                    texto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    color = azulPrimario,
                    letterSpacing = 1.sp
                )

                Text(
                    descripcion,
                    style = Typography.titleMedium,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}