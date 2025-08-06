package com.develop.tidytasks.data.repository.auth

import com.develop.tidytasks.data.model.AuthRequest
import com.develop.tidytasks.data.model.AuthResponse
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.data.remote.api.AuthApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(request: AuthRequest): Flow<NetworkResult<AuthResponse>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = authApi.login(request)
            emit(NetworkResult.Success(response))
        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.localizedMessage ?: "HTTP error"))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Network error"))
        }
    }

    override suspend fun register(request: AuthRequest): Flow<NetworkResult<AuthResponse>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = authApi.register(request)
            emit(NetworkResult.Success(response))
        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.localizedMessage ?: "HTTP error"))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Network error"))
        }
    }
}
