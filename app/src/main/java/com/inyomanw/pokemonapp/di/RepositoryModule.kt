package com.inyomanw.pokemonapp.di

import com.inyomanw.pokemonapp.data.RepositoryImplementation
import com.inyomanw.pokemonapp.data.local.SessionManager
import com.inyomanw.pokemonapp.data.local.UserLocalDataSource
import com.inyomanw.pokemonapp.data.remote.api.ApiService
import com.inyomanw.pokemonapp.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService,
        userLocalDataSource: UserLocalDataSource,
        sessionManager: SessionManager
    ): Repository = RepositoryImplementation(apiService, userLocalDataSource, sessionManager)
}