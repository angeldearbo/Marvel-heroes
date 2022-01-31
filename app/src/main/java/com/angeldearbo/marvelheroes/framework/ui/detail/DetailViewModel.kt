package com.angeldearbo.marvelheroes.framework.ui.detail

import androidx.lifecycle.*
import com.angeldearbo.marvelheroes.data.repository.exceptions.FatalError
import com.angeldearbo.marvelheroes.data.repository.exceptions.RetryError
import com.angeldearbo.marvelheroes.domain.Hero
import com.angeldearbo.marvelheroes.usecases.GetHeroById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getHeroById: GetHeroById,
    private val state: SavedStateHandle
) : ViewModel() {
    val heroId: Int = state.get<Int>(DetailActivity.HERO) ?: -1

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findHero()
            return _model
        }

    sealed class UiModel {
        data class Content(val hero: Hero) : UiModel()
        data class RetryError(val throwable: Throwable) : UiModel()
        data class Error(val throwable: Throwable) : UiModel()
    }

    fun findHero() {
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            when (ex) {
                is RetryError -> onRetryError(ex)
                is FatalError -> onError(ex)
            }
        }

        viewModelScope.launch(exceptionHandler) {
            _model.value = UiModel.Content(getHeroById.invoke(heroId))
        }
    }

    fun onRetryError(throwable: Throwable) {
        _model.value = UiModel.RetryError(throwable)
    }

    fun onError(throwable: Throwable) {
        _model.value = UiModel.Error(throwable)
    }
}