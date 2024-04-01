package com.example.presentation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

@InstallIn(SingletonComponent::class)
@Module
class DispatchersModule {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
