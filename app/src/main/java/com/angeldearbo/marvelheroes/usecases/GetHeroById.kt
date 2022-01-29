package com.angeldearbo.marvelheroes.usecases

import com.angeldearbo.marvelheroes.data.repository.HeroesRepository
import com.angeldearbo.marvelheroes.domain.Hero

class GetHeroById(private val heroesRepository: HeroesRepository) {
    suspend fun invoke(id: Int): Hero = heroesRepository.findById(id)
}