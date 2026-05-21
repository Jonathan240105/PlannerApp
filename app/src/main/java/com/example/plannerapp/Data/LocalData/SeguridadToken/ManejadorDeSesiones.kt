package com.example.plannerapp.Data.LocalData.SeguridadToken

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.auth0.android.jwt.JWT // 👈 Importamos la librería de Auth0
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "sesionUsuario")

@Singleton
class ManejadorDeSesiones @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val tokenJWT = stringPreferencesKey("token")
    private val rolAdmin = booleanPreferencesKey("es_admin")

    suspend fun guardarToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenJWT] = token

            try {
                val jwt = JWT(token)
                val rolClaim = jwt.getClaim("rol")
                val rol = rolClaim.asString() ?: ""

                preferences[rolAdmin] = rol.equals("admin", ignoreCase = true)
            } catch (e: Exception) {
                preferences[rolAdmin] = false
            }
        }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    val tokenFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[tokenJWT]
    }

    val esAdminFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[rolAdmin] ?: false
    }
}