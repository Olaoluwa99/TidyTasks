package com.develop.tidytasks.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    val USER_EMAIL = stringPreferencesKey("user_email")
}
