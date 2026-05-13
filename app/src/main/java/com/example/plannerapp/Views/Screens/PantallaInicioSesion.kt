package com.example.plannerapp.Views.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plannerapp.Views.ViewModels.InicioSesionViewModel
import kotlinx.coroutines.launch



@Composable
fun PantallaInicioSesion(paddingValues: PaddingValues, myViewModel: InicioSesionViewModel) {
    val model by myViewModel.model.collectAsState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título de la aplicación
        Text(
            text = "Tickly",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Bienvenido de nuevo",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // Campo de Email
        OutlinedTextField(
            value = model.email,
            onValueChange = { myViewModel.cambiarEmail(it) },
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Contraseña
        OutlinedTextField(
            value = model.contra,
            onValueChange = { myViewModel.cambiarContra(it) },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de Inicio de Sesión o Indicador de Carga
        if (model.cargando) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        } else {
            Button(
                onClick = {
                    scope.launch {
                        myViewModel.iniciarSesion(model.email, model.contra)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "Iniciar Sesión",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Mensaje de error (opcional si model.exito es falso después de cargar)
        if (!model.exito && !model.cargando && model.email.isNotEmpty()) {
            // Aquí podrías mostrar un pequeño texto de error si la lógica lo permite
        }
    }
}