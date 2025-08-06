package com.develop.tidytasks.data.remote.datasource

import com.develop.tidytasks.data.model.AuthRequest
import com.develop.tidytasks.data.model.AuthResponse
import com.develop.tidytasks.data.model.Todo
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.data.remote.api.AuthApi
import com.develop.tidytasks.data.remote.api.TodoApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
    private val todoApi: TodoApi
) : RemoteDataSource {

    override suspend fun login(request: AuthRequest): Flow<NetworkResult<AuthResponse>> = flow {
        try {
            val response = authApi.login(request)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun register(request: AuthRequest): Flow<NetworkResult<AuthResponse>> = flow {
        try {
            val response = authApi.register(request)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun getTodos(): Flow<NetworkResult<List<Todo>>> = flow {
        try {
            val response = todoApi.getTodos()
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun addTodo(todo: Todo): Flow<NetworkResult<Todo>> = flow {
        try {
            val response = todoApi.addTodo(todo)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun updateTodo(todo: Todo): Flow<NetworkResult<Todo>> = flow {
        try {
            val response = todoApi.updateTodo(todo.id, todo)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun deleteTodo(id: Int): Flow<NetworkResult<Unit>> = flow {
        try {
            todoApi.deleteTodo(id)
            emit(NetworkResult.Success(Unit))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}

