package com.develop.tidytasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develop.tidytasks.data.model.AuthRequest
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.data.remote.datasource.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {

    private val _testResult = MutableStateFlow<NetworkResult<Any>>(NetworkResult.Loading)
    val testResult: StateFlow<NetworkResult<Any>> = _testResult

    fun testLogin() {
        viewModelScope.launch {
            remoteDataSource.login(
                AuthRequest(email = "test@example.com", password = "password123")
            ).collect {
                _testResult.value = it
            }
        }
    }

    fun testGetTodos() {
        viewModelScope.launch {
            remoteDataSource.getTodos().collect {
                _testResult.value = it
            }
        }
    }
}
