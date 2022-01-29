package com.angeldearbo.marvelheroes.framework.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angeldearbo.marvelheroes.domain.Hero
import com.angeldearbo.marvelheroes.usecases.GetHeroesList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getHeroesList: GetHeroesList,
) : ViewModel() {
    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) {
                onCreate()
            }
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val heroes: List<Hero>) : UiModel()
        data class Navigation(val hero: Hero) : UiModel()
    }

    fun onCreate() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getHeroesList.invoke())
        }
    }

    fun onHeroClicked(hero: Hero) {
        _model.value = UiModel.Navigation(hero)
    }


}