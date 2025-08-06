package com.develop.tidytasks.data.repository.auth

import com.develop.tidytasks.data.model.AuthRequest
import com.develop.tidytasks.data.model.AuthResponse
import com.develop.tidytasks.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request: AuthRequest): Flow<NetworkResult<AuthResponse>>
    suspend fun register(request: AuthRequest): Flow<NetworkResult<AuthResponse>>
    fun isSignedIn(): Flow<Boolean>

}
