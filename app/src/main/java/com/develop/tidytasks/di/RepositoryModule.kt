package com.develop.tidytasks.di

import com.develop.tidytasks.data.repository.auth.AuthRepository
import com.develop.tidytasks.data.repository.auth.AuthRepositoryImpl
import com.develop.tidytasks.data.repository.todo.TodoRepository
import com.develop.tidytasks.data.repository.todo.TodoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTodoRepository(
        todoRepositoryImpl: TodoRepositoryImpl
    ): TodoRepository
}
