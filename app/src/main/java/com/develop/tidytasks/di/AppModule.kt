package com.develop.tidytasks.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideDispatcherMain(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
}
