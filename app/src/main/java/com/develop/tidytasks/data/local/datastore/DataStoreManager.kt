package com.develop.tidytasks.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Extension property on Context for DataStore instance
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // Save auth token
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[DataStoreKeys.AUTH_TOKEN] = token
        }
    }

    // Get auth token
    fun getAuthToken(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[DataStoreKeys.AUTH_TOKEN]
        }
    }

    // Save user email (optional)
    suspend fun saveUserEmail(email: String) {
        context.dataStore.edit { prefs ->
            prefs[DataStoreKeys.USER_EMAIL] = email
        }
    }

    // Get user email (optional)
    fun getUserEmail(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[DataStoreKeys.USER_EMAIL]
        }
    }

    // Clear all saved values (on logout)
    suspend fun clearData() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
