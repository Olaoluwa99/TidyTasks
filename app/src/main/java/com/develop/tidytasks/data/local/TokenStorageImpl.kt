package com.develop.tidytasks.data.local

import com.develop.tidytasks.data.local.datastore.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenStorageImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : TokenStorage {

    override suspend fun saveToken(token: String) {
        dataStoreManager.saveAuthToken(token)
    }

    override fun getToken(): Flow<String?> {
        return dataStoreManager.getAuthToken()
    }

    override suspend fun clearToken() {
        dataStoreManager.clearData()
    }
}
