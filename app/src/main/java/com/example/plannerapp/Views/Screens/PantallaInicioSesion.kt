package com.example.plannerapp.Views.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plannerapp.Views.ViewModels.InicioSesionViewModel
import com.example.plannerapp.ui.theme.Typography
import com.example.plannerapp.ui.theme.azulPrimario
import com.example.plannerapp.ui.theme.colorError
import kotlinx.coroutines.launch

@Composable
fun PantallaInicioSesion(
    paddingValues: PaddingValues,
    myViewModel: InicioSesionViewModel,
    cambiarAPrincipal: () -> Unit
) {
    val model by myViewModel.model.collectAsState()
    var contraVisible by remember { mutableStateOf(false) }

    val isError = model.exito == false && model.cargando == false
    LaunchedEffect(model.exito) {
        if (model.exito == true) {
            myViewModel.resetearModel()
            cambiarAPrincipal()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            "Tickly",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 50.sp,
            color = azulPrimario
        )

        Text(
            "Gestiona tus tareas de forma inteligente.",
            style = Typography.titleMedium,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 5.dp, bottom = 40.dp)
        )

        OutlinedTextField(
            model.email,
            { myViewModel.cambiarEmail(it) },
            label = { Text("Correo electrónico") },
            leadingIcon = {
                Icon(
                    Icons.Rounded.Email,
                    "",
                    tint = if (isError) colorError else azulPrimario
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = azulPrimario, focusedLabelColor = azulPrimario
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            model.contra,
            { myViewModel.cambiarContra(it) },
            label = { Text("Contraseña") },
            leadingIcon = {
                Icon(
                    Icons.Rounded.Lock,
                    contentDescription = null,
                    tint = if (isError) colorError else azulPrimario
                )
            },
            visualTransformation = if (contraVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icono = if (contraVisible) Icons.Rounded.Lock else Icons.Rounded.AddCircle
                IconButton({ contraVisible = !contraVisible }) {
                    Icon(icono, "")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = if (contraVisible) KeyboardType.Text else KeyboardType.Password),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = azulPrimario, focusedLabelColor = azulPrimario
            )
        )

        Spacer(Modifier.height(30.dp))
        botonInicioSesion(
            model.cargando == true,
            { myViewModel.iniciarSesion(model.email, model.contra) })

        Spacer(Modifier.height(10.dp))
        textoError(isError)
    }
}

@Composable
fun botonInicioSesion(mostrarCirculoCargando: Boolean, iniciarSesion: () -> Unit) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (mostrarCirculoCargando) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp), color = azulPrimario, strokeWidth = 4.dp
            )
        } else {
            Button(
                onClick = {
                    iniciarSesion()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = azulPrimario)
            ) {
                Text(
                    "Iniciar Sesión",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun textoError(error: Boolean) {
    AnimatedVisibility(
        visible = error, enter = fadeIn(), exit = fadeOut()
    ) {
        Text(
            "Error al iniciar sesión",
            color = colorError,
            style = Typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}