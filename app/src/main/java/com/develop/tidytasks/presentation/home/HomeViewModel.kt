package com.develop.tidytasks.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develop.tidytasks.data.model.Todo
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.data.repository.auth.AuthRepository
import com.develop.tidytasks.data.repository.todo.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _testResult = MutableStateFlow<NetworkResult<Any>>(NetworkResult.Loading)
    val testResult: StateFlow<NetworkResult<Any>> = _testResult

    fun getTodos() {
        viewModelScope.launch {
            todoRepository.getTodos().collect {
                _testResult.value = it
            }
        }
    }

    fun addTodo() {
        viewModelScope.launch {
            todoRepository.addTodo(Todo(id = 0, todo = "Dance and play in the Car", completed = false, userId = 1))
                .collect {
                    _testResult.value = it
                }
        }
    }

}