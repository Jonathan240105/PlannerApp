package com.example.plannerapp.Data.LocalData

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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

    suspend fun guardarToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenJWT] = token
        }
    }

    val tokenFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[tokenJWT]
    }

}