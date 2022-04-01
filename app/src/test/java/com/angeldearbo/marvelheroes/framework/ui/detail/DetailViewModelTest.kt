package com.angeldearbo.marvelheroes.framework.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.angeldearbo.marvelheroes.usecases.GetHeroById
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @Mock
    lateinit var getHeroById: GetHeroById

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

//    @Test
//    fun `Find hero gets data`() = runTest {
//        val hero = Hero(1, "Name", "uri", "Description")
//        val map = mapOf(Pair<String,Object>())
//        whenever(getHeroById.invoke(1)).thenReturn(hero)
//        val vm = DetailViewModel(getHeroById, SavedStateHandle())
//        vm.model.observeForever(observer)
//        Mockito.verify(observer).onChanged(DetailViewModel.UiModel.Content(hero))
//    }
}