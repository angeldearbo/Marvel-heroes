package com.angeldearbo.marvelheroes.framework.ui.detail

import androidx.lifecycle.*
import com.angeldearbo.marvelheroes.domain.Hero
import com.angeldearbo.marvelheroes.usecases.GetHeroById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getHeroById: GetHeroById,
    private val state: SavedStateHandle
) : ViewModel() {
    val heroId: Int = state.get<Int>(DetailActivity.HERO) ?: -1

    data class UiModel(val hero: Hero)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findHero()
            return _model
        }

    private fun findHero() = viewModelScope.launch {
        _model.value = UiModel(getHeroById.invoke(heroId))
    }
}