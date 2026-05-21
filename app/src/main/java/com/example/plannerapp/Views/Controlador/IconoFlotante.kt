package com.example.plannerapp.Views.Controlador

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.plannerapp.ui.theme.azulPrimario

@Composable
fun IconoFlotante(nuevaLista: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = nuevaLista,
        containerColor = azulPrimario,
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(Icons.Rounded.Add, contentDescription = "Añadir Lista")
        Spacer(modifier = Modifier.width(8.dp))
        Text("Nueva Lista", fontWeight = FontWeight.Bold)
    }
}
