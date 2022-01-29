package com.angeldearbo.marvelheroes.usecases

import com.angeldearbo.marvelheroes.data.repository.HeroesRepository
import com.angeldearbo.marvelheroes.domain.Hero

class GetHeroesList(private val heroesRepository: HeroesRepository) {
    suspend fun invoke(): List<Hero> = heroesRepository.getHeroesList()
}