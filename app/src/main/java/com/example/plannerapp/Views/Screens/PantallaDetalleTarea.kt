package com.example.plannerapp.Views.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.plannerapp.Domain.Subtarea
import com.example.plannerapp.Views.ViewModels.DetalleTareaViewModel
import com.example.plannerapp.ui.theme.Typography
import com.example.plannerapp.ui.theme.azulPrimario

@Composable
fun PantallaDetalleTarea(
    paddingValues: PaddingValues,
    myViewModel: DetalleTareaViewModel,
    idTarea: Int
) {
    val model by myViewModel.model.collectAsState()
    var mostrarDialogoSubtarea by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        myViewModel.cargarSubtareas(idTarea)
        myViewModel.cargarTarea(idTarea)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (model.cargandoTarea) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = azulPrimario)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(1.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                model.tarea.titulo ?: "Sin Título",
                                style = Typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                model.tarea.descripcion ?: "Sin descripción disponible.",
                                style = Typography.bodyLarge,
                                fontSize = 16.sp,
                                color = Color.DarkGray
                            )

                            Spacer(Modifier.height(20.dp))
                            HorizontalDivider(color = Color.Gray.copy(alpha = 0.15f))
                            Spacer(Modifier.height(16.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "Fecha Límite: ",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                                Text(
                                    text = model.tarea.fechaLimite ?: "No establecida",
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }

                item {
                    Card(
                        Modifier.padding(1.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Subtareas (${model.listaSubtareas.size})",
                                    style = Typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(onClick = { mostrarDialogoSubtarea = true }) {
                                    Icon(Icons.Rounded.Add, "Añadir Subtarea", tint = azulPrimario)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            if (model.cargandoSubtareas) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 20.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = azulPrimario)
                                }
                            } else if (model.listaSubtareas.isEmpty()) {
                                Text(
                                    text = "No hay subtareas añadidas para este elemento",
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp)
                                )
                            } else {
                                model.listaSubtareas.forEach { subtarea ->
                                    EstructuraSubtarea(
                                        subtarea = subtarea,
                                        onCambioEstado = {
                                            subtarea.id?.let { id ->
                                                myViewModel.marcarSubtarea(id, idTarea)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (mostrarDialogoSubtarea) {
        DialogoNuevaSubtarea(
            onDismiss = { mostrarDialogoSubtarea = false },
            onConfirm = {
            }
        )
    }
}

@Composable
fun EstructuraSubtarea(
    subtarea: Subtarea,
    onCambioEstado: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 1.dp),
        colors = CardDefaults.cardColors(containerColor = azulPrimario.copy(alpha = 0.12f)),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = subtarea.titulo,
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = if (subtarea.estado == true) Color.Gray else Color.Black,
                modifier = Modifier.weight(1f)
            )

            Checkbox(
                checked = subtarea.estado ?: false,
                onCheckedChange = {
                    onCambioEstado()
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = azulPrimario,
                    uncheckedColor = Color.Gray
                )
            )
        }
    }
}

@Composable
fun DialogoNuevaSubtarea(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var tituloSubtarea by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Nueva Subtarea",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = Color.Black
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Divide el trabajo de esta tarea en pasos más pequeños.",
                    color = Color.DarkGray,
                    fontSize = 15.sp
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = tituloSubtarea,
                    onValueChange = { tituloSubtarea = it },
                    label = { Text("Nombre del paso / subtarea") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = azulPrimario,
                        focusedLabelColor = azulPrimario,
                        cursorColor = azulPrimario
                    )
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = Color.Gray, fontWeight = FontWeight.Medium)
                    }

                    Spacer(Modifier.width(8.dp))

                    TextButton(
                        onClick = {
                            if (tituloSubtarea.isNotBlank()) {
                                onConfirm(tituloSubtarea)
                            }
                        }
                    ) {
                        Text("Añadir", color = azulPrimario, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}