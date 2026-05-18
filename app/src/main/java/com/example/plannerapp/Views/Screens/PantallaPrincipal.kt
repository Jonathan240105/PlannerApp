package com.example.plannerapp.Views.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plannerapp.Domain.ListaConTareas
import com.example.plannerapp.ui.theme.Typography
import com.example.plannerapp.ui.theme.azulPrimario

@Composable
fun PantallaPrincipal(
    paddingValues: PaddingValues,
    listasConTareas: List<ListaConTareas>,
    onTareaClick: (Int) -> Unit,
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                "Mi WorkSpace",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 35.sp,
                color = azulPrimario
            )
            Text(
                "Espacio de trabajo en equipo.",
                style = Typography.titleMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        if (listasConTareas.isEmpty()) {
            item {
                Text(
                    "No hay listas creadas en este tablero",
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 40.dp)
                )
            }
        } else {
            items(listasConTareas) { lista ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${lista.nombreLista} (${lista.listaTareas.size})",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    IconButton(onClick = {}) {
                        Icon(Icons.Rounded.Add, "", tint = azulPrimario)
                    }
                }

                if (lista.listaTareas.isNotEmpty()) {
                    LinearProgressIndicator(
                        progress = { 0.5f },
                        color = azulPrimario,
                        trackColor = Color(0xFFE0E0E0),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 12.dp)
                            .height(4.dp)
                    )
                }

                lista.listaTareas.forEach { tarea ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onTareaClick(tarea.idTarea) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F5F7)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = tarea.titulo,
                            modifier = Modifier.padding(16.dp),
                            style = Typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}