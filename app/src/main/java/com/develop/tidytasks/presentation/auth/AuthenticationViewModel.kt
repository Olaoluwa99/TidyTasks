package com.develop.tidytasks.presentation.auth

import androidx.lifecycle.ViewModel
import com.develop.tidytasks.data.repository.auth.AuthRepository
import com.develop.tidytasks.data.repository.todo.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

}