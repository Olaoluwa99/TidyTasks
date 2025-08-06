package com.develop.tidytasks.data.remote.datasource

import com.develop.tidytasks.data.model.AuthResponse
import com.develop.tidytasks.data.model.AuthRequest
import com.develop.tidytasks.data.model.Todo
import com.develop.tidytasks.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun login(request: AuthRequest): Flow<NetworkResult<AuthResponse>>
    suspend fun register(request: AuthRequest): Flow<NetworkResult<AuthResponse>>
    suspend fun getTodos(): Flow<NetworkResult<List<Todo>>>
    suspend fun addTodo(todo: Todo): Flow<NetworkResult<Todo>>
    suspend fun updateTodo(todo: Todo): Flow<NetworkResult<Todo>>
    suspend fun deleteTodo(id: Int): Flow<NetworkResult<Unit>>
}
