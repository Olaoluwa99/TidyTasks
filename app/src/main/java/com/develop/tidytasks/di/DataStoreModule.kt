package com.develop.tidytasks.di

import android.content.Context
import com.develop.tidytasks.data.local.TokenStorage
import com.develop.tidytasks.data.local.datastore.DataStoreManager
import com.develop.tidytasks.data.local.TokenStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(
        @ApplicationContext context: Context
    ): DataStoreManager = DataStoreManager(context)

    @Provides
    @Singleton
    fun provideTokenStorage(
        dataStoreManager: DataStoreManager
    ): TokenStorage = TokenStorageImpl(dataStoreManager)
}
