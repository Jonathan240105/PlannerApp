package com.example.plannerapp.Views.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plannerapp.Views.ViewModels.PerfilViewModel
import com.example.plannerapp.ui.theme.azulPrimario

@Composable
fun PantallaPerfil(
    paddingValues: PaddingValues,
    myViewModel: PerfilViewModel,
    cerrarSesion: () -> Unit
) {
    val model by myViewModel.model.collectAsState()

    LaunchedEffect(Unit) {
        myViewModel.cargarPerfilUsuario()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            if (model.perfilUsuario != null) {
                val usuario = model.perfilUsuario!!

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .background(azulPrimario.copy(alpha = 0.12f), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = usuario.foto?.take(1)?.uppercase() ?: "?",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = azulPrimario
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = usuario.nombre ?: "Usuario",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        val esAdmin = usuario.rol?.equals("admin", ignoreCase = true) == true
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (esAdmin) Color(0xFFFFEBEE) else azulPrimario.copy(
                                    alpha = 0.1f
                                )
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(
                                text = (usuario.rol ?: "usuario").uppercase(),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (esAdmin) Color.Red else azulPrimario
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.15f))
                        Spacer(modifier = Modifier.height(16.dp))

                        ItemInfoPerfil(
                            icon = Icons.Rounded.Email,
                            titulo = "Correo Electrónico",
                            valor = usuario.email ?: "No disponible"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        val idTexto = usuario.id?.toString() ?: "0"
                        ItemInfoPerfil(
                            icon = Icons.Rounded.Face,
                            titulo = "ID de Cuenta",
                            valor = "#$idTexto"
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = azulPrimario)
                }
            }

            Button(
                onClick = { myViewModel.cerrarSesion(); cerrarSesion() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = azulPrimario,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.ExitToApp,
                    ""
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    "Cerrar Sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun ItemInfoPerfil(icon: ImageVector, titulo: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(36.dp)
                .background(Color.Gray.copy(alpha = 0.08f), shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                "",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                titulo,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            Text(
                valor,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}