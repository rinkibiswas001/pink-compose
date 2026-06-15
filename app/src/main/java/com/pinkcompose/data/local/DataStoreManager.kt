package com.pinkcompose.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.pinkcompose.domain.model.Login
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class DataStoreManager @Inject constructor(
    private val context: Context
) {

    companion object {
        val USER_ID = stringPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
    }

    suspend fun saveUser(user: Login) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = user.id
            preferences[USER_NAME] = user.username
            preferences[USER_EMAIL] = user.email
        }
    }

    val getUser: Flow<Login?> = context.dataStore.data.map { preferences ->
        val id = preferences[USER_ID] ?: return@map null
        val name = preferences[USER_NAME] ?: ""
        val email = preferences[USER_EMAIL] ?: ""
        Login(id, name, email, "")
    }

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
