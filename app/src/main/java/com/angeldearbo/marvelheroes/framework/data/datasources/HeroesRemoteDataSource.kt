package com.angeldearbo.marvelheroes.framework.data.datasources

import com.angeldearbo.marvelheroes.data.datasources.RemoteDataSource
import com.angeldearbo.marvelheroes.domain.Hero

class HeroesRemoteDataSource(
    private val marvelService: MarvelService
) : RemoteDataSource {
    override suspend fun getHeroesList(): List<Hero> {
        val response = marvelService.getSuperheroes()
        return response.data.results.map { it ->
            Hero(
                it.id,
                it.name,
                it.thumbnail.getHttpUrl(),
                it.description
            )
        }
    }

    override suspend fun getHeroById(id: Int): Hero {
        val response = marvelService.getSuperheroDetails(id)
        val it = response.data.results[0]
        return Hero(
            it.id,
            it.name,
            it.thumbnail.getHttpUrl(),
            it.description
        )
    }

}