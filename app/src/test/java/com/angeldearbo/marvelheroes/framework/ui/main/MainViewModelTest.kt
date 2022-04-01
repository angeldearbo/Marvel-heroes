package com.angeldearbo.marvelheroes.framework.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.angeldearbo.marvelheroes.domain.Hero
import com.angeldearbo.marvelheroes.usecases.GetHeroesList
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @Mock
    lateinit var getHeroesList: GetHeroesList

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

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

    @Test
    fun `On create loads heroes`() = runTest {
        val list = listOf<Hero>(Hero(1, "Name", "uri", "Description"))

        whenever(getHeroesList.invoke()).thenReturn(list)
        val vm = MainViewModel(getHeroesList)
        vm.model.observeForever(observer)
        verify(observer).onChanged(MainViewModel.UiModel.Content(list))
    }
}

