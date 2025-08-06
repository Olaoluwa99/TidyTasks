package com.develop.tidytasks.data.repository.todo

import com.develop.tidytasks.data.model.Todo
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.data.remote.api.TodoApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoApi: TodoApi
) : TodoRepository {

    override suspend fun getTodos(): Flow<NetworkResult<List<Todo>>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = todoApi.getTodos()
            emit(NetworkResult.Success(response))
        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.localizedMessage ?: "HTTP error"))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Network error"))
        }
    }

    override suspend fun addTodo(todo: Todo): Flow<NetworkResult<Todo>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = todoApi.addTodo(todo)
            emit(NetworkResult.Success(response))
        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.localizedMessage ?: "HTTP error"))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Network error"))
        }
    }

    override suspend fun updateTodo(id: Int, todo: Todo): Flow<NetworkResult<Todo>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = todoApi.updateTodo(id, todo)
            emit(NetworkResult.Success(response))
        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.localizedMessage ?: "HTTP error"))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Network error"))
        }
    }

    override suspend fun deleteTodo(id: Int): Flow<NetworkResult<Unit>> = flow {
        emit(NetworkResult.Loading)
        try {
            todoApi.deleteTodo(id)
            emit(NetworkResult.Success(Unit))
        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.localizedMessage ?: "HTTP error"))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Network error"))
        }
    }
}
