package com.example.remote.config

import com.example.remote.movies.MoviesAPIs
import com.example.remote.movies.MoviesService
import com.example.remote.movies.MoviesServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {

    @Singleton
    @Provides
    @Named("HttpLogging")
    fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Named("ApiTimeout")
    fun provideApiTimeout(): Long = 30_000 // 30 Seconds

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        @Named("ApiTimeout") timeout: Long,
        authInterceptor: AuthInterceptor,
        @Named("HttpLogging") loggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder().run {
            readTimeout(timeout, TimeUnit.MILLISECONDS)
            connectTimeout(timeout, TimeUnit.MILLISECONDS)
            addInterceptor(authInterceptor)
            addInterceptor(loggingInterceptor)
            build()
        }

    @Provides
    @Named("BASE_URL_API")
    fun provideAPIBaseUrl(): String {
        return BASE_URL_API
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        @Named("BASE_URL_API") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesAPIs(retrofit: Retrofit): MoviesAPIs {
        return retrofit.create(MoviesAPIs::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesService(moviesAPIs: MoviesAPIs): MoviesService {
        return MoviesServiceImpl(moviesAPIs)
    }

    companion object {
        private const val BASE_URL_API = "https://api.themoviedb.org/3/"
    }
}