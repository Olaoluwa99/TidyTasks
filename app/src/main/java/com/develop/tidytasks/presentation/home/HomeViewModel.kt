package com.develop.tidytasks.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develop.tidytasks.data.model.Todo
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.data.repository.todo.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _todoList = MutableStateFlow<List<Todo>>(emptyList())
    val todoList: StateFlow<List<Todo>> = _todoList

    private val _status = MutableStateFlow<NetworkResult<Unit>>(NetworkResult.Success(Unit))
    val status: StateFlow<NetworkResult<Unit>> = _status

    init {
        getTodos()
    }

    fun getTodos() {
        viewModelScope.launch {
            _status.value = NetworkResult.Loading
            todoRepository.getTodos().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _todoList.value = result.data.todos
                        _status.value = NetworkResult.Success(Unit)
                    }
                    is NetworkResult.Error -> {
                        _status.value = NetworkResult.Error(result.message)
                    }
                    is NetworkResult.Loading -> {
                        _status.value = NetworkResult.Loading
                    }
                }
            }
        }
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            _status.value = NetworkResult.Loading
            todoRepository.addTodo(todo).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _todoList.update { currentList ->
                            currentList + result.data
                        }
                        _status.value = NetworkResult.Success(Unit)
                    }
                    is NetworkResult.Error -> {
                        _status.value = NetworkResult.Error(result.message)
                    }
                    is NetworkResult.Loading -> {
                        _status.value = NetworkResult.Loading
                    }
                }
            }
        }
    }

    fun updateTodo(updatedTodo: Todo) {
        viewModelScope.launch {
            _status.value = NetworkResult.Loading
            todoRepository.updateTodo(updatedTodo).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _todoList.update { currentList ->
                            currentList.map { if (it.id == updatedTodo.id) updatedTodo else it }
                        }
                        _status.value = NetworkResult.Success(Unit)
                    }
                    is NetworkResult.Error -> {
                        _status.value = NetworkResult.Error(result.message)
                    }
                    is NetworkResult.Loading -> {
                        _status.value = NetworkResult.Loading
                    }
                }
            }
        }
    }

    fun deleteTodo(todoId: Int) {
        viewModelScope.launch {
            _status.value = NetworkResult.Loading
            todoRepository.deleteTodo(todoId).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _todoList.update { currentList ->
                            currentList.filterNot { it.id == todoId }
                        }
                        _status.value = NetworkResult.Success(Unit)
                    }
                    is NetworkResult.Error -> {
                        _status.value = NetworkResult.Error(result.message)
                    }
                    is NetworkResult.Loading -> {
                        _status.value = NetworkResult.Loading
                    }
                }
            }
        }
    }

    fun getCompletedTodos(): List<Todo> {
        return todoList.value.filter { it.completed }
    }

    fun getInProgressTodos(): List<Todo> {
        return todoList.value.filter { !it.completed }
    }

    fun todoExists(id: Int): Boolean {
        return todoList.value.any { it.id == id }
    }

    fun generateRandomId(): Int {
        return (1000..9999).random()
    }

}
