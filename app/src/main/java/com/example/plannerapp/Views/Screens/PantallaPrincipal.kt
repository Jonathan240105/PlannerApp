package com.example.plannerapp.Views.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.plannerapp.Domain.ListaConTareas
import com.example.plannerapp.Domain.Tarea
import com.example.plannerapp.Views.ViewModels.PrincipalViewModel
import com.example.plannerapp.ui.theme.Typography
import com.example.plannerapp.ui.theme.azulPrimario

@Composable
fun PantallaPrincipal(
    paddingValues: PaddingValues,
    myViewModel: PrincipalViewModel,
    mostrarDialog: Boolean,
    onCambiarMostrarDialog: (Boolean) -> Unit
) {

    LaunchedEffect(Unit) {
        myViewModel.getListasConTareas()
    }
    val model by myViewModel.model.collectAsState()

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        if (model.listas.isEmpty()) {
            item {
                Text(
                    "No hay listas creadas en este tablero",
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 40.dp)
                )
            }
        } else {
            items(model.listas) { lista ->
                EstructuraLista(lista)
            }
        }
    }
    if (mostrarDialog) {
        DialogoPersonalizadoNuevaLista({
            onCambiarMostrarDialog(false)
        }) {
            myViewModel.crearLista(it)
            onCambiarMostrarDialog(false)
        }
    }
}

@Composable
fun EstructuraLista(lista: ListaConTareas) {
    Card(
        Modifier.padding(1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${lista.nombreLista} (${lista.listaTareas.size})",
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Rounded.Edit, "Editar", tint = Color.Gray)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Rounded.Add, "Añadir", tint = azulPrimario)
                    }
                }
            }
            lista.listaTareas.forEach { tarea ->
                EstructuraTarea(tarea)
            }
        }
    }
}

@Composable
fun EstructuraTarea(tarea: Tarea) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {},
        colors = CardDefaults.cardColors(containerColor = azulPrimario.copy(alpha = 0.15f)),
        shape = RoundedCornerShape(12.dp),
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

@Composable
fun DialogoPersonalizadoNuevaLista(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var nombreLista by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Nueva lista",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Introduce el nombre para organizar tus tareas.",
                    color = Color.DarkGray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = nombreLista,
                    onValueChange = { nombreLista = it },
                    label = { Text("Nombre de la lista") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = azulPrimario,
                        focusedLabelColor = azulPrimario,
                        cursorColor = azulPrimario
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Controlamos los botones manualmente en una Row alineada al final
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = Color.Gray, fontWeight = FontWeight.Medium)
                    }

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    TextButton(
                        onClick = {
                            if (nombreLista.isNotBlank()) {
                                onConfirm(nombreLista)
                            }
                        }
                    ) {
                        Text("Crear", color = azulPrimario, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}