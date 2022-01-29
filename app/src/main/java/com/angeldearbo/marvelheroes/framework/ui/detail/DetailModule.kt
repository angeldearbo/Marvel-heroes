package com.angeldearbo.marvelheroes.framework.ui.detail

import com.angeldearbo.marvelheroes.data.repository.HeroesRepository
import com.angeldearbo.marvelheroes.usecases.GetHeroById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DetailModule {

    @Provides
    fun getHeroByIdProvider(repository: HeroesRepository): GetHeroById {
        return GetHeroById(repository)
    }
}