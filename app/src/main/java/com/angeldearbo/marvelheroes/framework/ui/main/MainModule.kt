package com.angeldearbo.marvelheroes.framework.ui.main

import com.angeldearbo.marvelheroes.data.repository.HeroesRepository
import com.angeldearbo.marvelheroes.usecases.GetHeroesList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainModule {

    @Provides
    fun getHeroesListProvider(repository: HeroesRepository): GetHeroesList {
        return GetHeroesList(repository)
    }
}