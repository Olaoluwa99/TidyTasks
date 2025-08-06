package com.develop.tidytasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develop.tidytasks.data.model.AuthRequest
import com.develop.tidytasks.data.model.Todo
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.data.remote.datasource.RemoteDataSource
import com.develop.tidytasks.data.repository.auth.AuthRepository
import com.develop.tidytasks.data.repository.todo.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _testResult = MutableStateFlow<NetworkResult<Any>>(NetworkResult.Loading)
    val testResult: StateFlow<NetworkResult<Any>> = _testResult

    /*private val _userId = MutableStateFlow<String>(NetworkResult.Loading)
    val userId: StateFlow<NetworkResult<Any>> = _userId*/

    /*private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn*/

    val isSignedIn: StateFlow<Boolean> = authRepository
        .isSignedIn()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )


    fun testLogin() {
        viewModelScope.launch {
            authRepository.login(
                AuthRequest(username = "emilys", password = "emilyspass")
            ).collect {
                _testResult.value = it
            }
        }
    }

    fun testGetTodos() {
        viewModelScope.launch {
            todoRepository.getTodos().collect {
                _testResult.value = it
            }
        }
    }

    fun testAddTodo() {
        viewModelScope.launch {
            todoRepository.addTodo(Todo(id = 0, todo = "Dance and play in the Car", completed = false, userId = 1))
                .collect {
                    _testResult.value = it
                }
        }
    }

}
