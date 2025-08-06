package com.develop.tidytasks.data.remote.api

import com.develop.tidytasks.data.model.AuthRequest
import com.develop.tidytasks.data.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body request: AuthRequest
    ): AuthResponse

    @POST("auth/register")
    suspend fun register(
        @Body request: AuthRequest
    ): AuthResponse
}