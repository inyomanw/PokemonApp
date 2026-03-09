package com.inyomanw.pokemonapp.di

import com.inyomanw.pokemonapp.data.RepositoryImplementation
import com.inyomanw.pokemonapp.data.local.SessionManager
import com.inyomanw.pokemonapp.data.source.LocalDataSource
import com.inyomanw.pokemonapp.data.source.RemoteDataSource
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
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        sessionManager: SessionManager
    ): Repository = RepositoryImplementation(remoteDataSource, localDataSource, sessionManager)
}