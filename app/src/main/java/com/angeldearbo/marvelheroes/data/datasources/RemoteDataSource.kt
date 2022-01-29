package com.angeldearbo.marvelheroes.data.datasources

import com.angeldearbo.marvelheroes.domain.Hero

interface RemoteDataSource {
    suspend fun getHeroesList(): List<Hero>
    suspend fun getHeroById(id: Int): Hero
}