package com.example.repo

import com.example.remote.movies.MoviesService
import com.example.repo.movies.MoviesRepoImpl
import com.example.usecases.movies.repos.MoviesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideMoviesRepo(moviesService: MoviesService): MoviesRepo {
        return MoviesRepoImpl(moviesService)
    }
}