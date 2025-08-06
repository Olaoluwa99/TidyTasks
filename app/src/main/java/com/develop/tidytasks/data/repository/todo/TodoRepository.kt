package com.develop.tidytasks.data.repository.todo

import com.develop.tidytasks.data.model.Todo
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.data.remote.api.TodoResponse
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getTodos(): Flow<NetworkResult<TodoResponse>>
    suspend fun addTodo(todo: Todo): Flow<NetworkResult<Todo>>
    suspend fun updateTodo(todo: Todo): Flow<NetworkResult<Todo>>
    suspend fun deleteTodo(id: Int): Flow<NetworkResult<Unit>>
}
