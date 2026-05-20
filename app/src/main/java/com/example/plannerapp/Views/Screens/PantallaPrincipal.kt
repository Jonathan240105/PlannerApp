package com.example.plannerapp.Views.Screens

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.example.plannerapp.Data.RemoteData.Responses.CrearTareaSolicitud
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
    onCambiarMostrarDialog: (Boolean) -> Unit,
    irADetalle: (Int) -> Unit
) {
    val model by myViewModel.model.collectAsState()

    var idListaSeleccionada by remember { mutableStateOf<Int?>(null) }
    val listasAMostrar = if (model.vistasEquipo) model.listasEquipo else model.listas

    LaunchedEffect(Unit) {
        myViewModel.getListasConTareas()
        myViewModel.cargarDatosEquipo()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        TabRow(
            selectedTabIndex = if (model.vistasEquipo) 1 else 0,
            containerColor = Color.White,
            contentColor = azulPrimario
        ) {
            Tab(
                selected = !model.vistasEquipo,
                onClick = { myViewModel.cambiarVista(false) },
                text = { Text("Mis Tareas", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = model.vistasEquipo,
                onClick = { myViewModel.cambiarVista(true) },
                text = { Text("Mi Equipo", fontWeight = FontWeight.Bold) }
            )
        }


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            if (listasAMostrar.isEmpty()) {
                item {
                    Text(
                        if (model.vistasEquipo) "No hay listas de equipo disponibles" else "No hay listas creadas en este tablero",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 40.dp)
                    )
                }
            } else {
                items(listasAMostrar) { lista ->
                    EstructuraLista(
                        lista = lista,
                        onAñadirTarea = { idListaSeleccionada = lista.idLista },
                        irADetalle
                    )
                }
            }
        }
    }

    if (idListaSeleccionada != null) {
        DialogoNuevaTarea(
            esAdmin = model.esAdmin,
            miembros = model.miembrosEquipo.map {
                Pair(
                    it.idUsuario,
                    it.nombreUsuario
                )
            },
            onDismiss = { idListaSeleccionada = null },
            onConfirm = { titulo, descripcion, fecha, idAsignado ->
                val body = CrearTareaSolicitud(
                    titulo = titulo,
                    descripcion = descripcion,
                    fecha_limite = fecha
                )
                myViewModel.crearTarea(body, idAsignado, idListaSeleccionada!!)
                idListaSeleccionada = null
            }
        )
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
fun EstructuraLista(lista: ListaConTareas, onAñadirTarea: () -> Unit,irADetalle: (Int) -> Unit) {
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
                    IconButton(onClick = onAñadirTarea) {
                        Icon(Icons.Rounded.Add, "Añadir", tint = azulPrimario)
                    }
                }
            }
            lista.listaTareas.forEach { tarea ->
                EstructuraTarea(tarea, irADetalle)
            }
        }
    }
}

@Composable
fun EstructuraTarea(tarea: Tarea, irADetalle: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 5.dp)
            .clickable { irADetalle(tarea.idTarea) },
        colors = CardDefaults.cardColors(
            containerColor = azulPrimario.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = tarea.titulo.ifBlank { "Tarea sin título" },
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoNuevaTarea(
    esAdmin: Boolean,
    miembros: List<Pair<Int, String>>,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, Int) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    var dropdownExpandido by remember { mutableStateOf(false) }
    var miembroSeleccionado by remember { mutableStateOf<Pair<Int, String>?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                Modifier
                    .padding(22.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "Nueva Tarea",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = Color.Black
                )
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = azulPrimario,
                        focusedLabelColor = azulPrimario
                    )
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = azulPrimario,
                        focusedLabelColor = azulPrimario
                    )
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha Límite (fecha y hora)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = azulPrimario,
                        focusedLabelColor = azulPrimario
                    )
                )

                if (esAdmin) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Asignar a:",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(6.dp))

                    ExposedDropdownMenuBox(
                        expanded = dropdownExpandido,
                        onExpandedChange = { dropdownExpandido = it }
                    ) {
                        OutlinedTextField(
                            value = miembroSeleccionado?.second ?: "Selecciona un usuario",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpandido) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azulPrimario)
                        )
                        ExposedDropdownMenu(
                            expanded = dropdownExpandido,
                            onDismissRequest = { dropdownExpandido = false }
                        ) {
                            miembros.forEach { miembro ->
                                DropdownMenuItem(
                                    text = { Text(miembro.second) },
                                    onClick = {
                                        miembroSeleccionado = miembro
                                        dropdownExpandido = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = Color.Gray)
                    }
                    Spacer(Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            if (titulo.isNotBlank()) {
                                val idAsignadoFinal =
                                    if (esAdmin) (miembroSeleccionado?.first ?: 0) else 0
                                onConfirm(titulo, descripcion, fecha, idAsignadoFinal)
                            }
                        }
                    ) {
                        Text("Crear Tarea", color = azulPrimario, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
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
                    "Nueva lista",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = Color.Black
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Introduce el nombre para organizar tus tareas.",
                    color = Color.DarkGray,
                    fontSize = 15.sp
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    nombreLista,
                    { nombreLista = it },
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

                Spacer(Modifier.height(24.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = Color.Gray, fontWeight = FontWeight.Medium)
                    }

                    Spacer(
                        Modifier.width(8.dp)
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