package com.angeldearbo.marvelheroes.data

import com.angeldearbo.marvelheroes.data.datasources.RemoteDataSource
import com.angeldearbo.marvelheroes.data.repository.HeroesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun heroesRepositoryProvider(remoteDataSource: RemoteDataSource): HeroesRepository {
        return HeroesRepository(remoteDataSource)
    }
}