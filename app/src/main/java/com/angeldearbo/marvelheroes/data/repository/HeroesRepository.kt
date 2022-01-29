package com.angeldearbo.marvelheroes.data.repository

import com.angeldearbo.marvelheroes.data.datasources.RemoteDataSource
import com.angeldearbo.marvelheroes.domain.Hero

class HeroesRepository(
    private val remoteDataSource: RemoteDataSource,
) {
    suspend fun getHeroesList(): List<Hero> {
        return remoteDataSource.getHeroesList()
    }

    suspend fun findById(id: Int): Hero = remoteDataSource.getHeroById(id)

}