package com.angeldearbo.marvelheroes.framework.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.angeldearbo.marvelheroes.databinding.ActivityMainBinding
import com.angeldearbo.marvelheroes.framework.ui.common.startActivity
import com.angeldearbo.marvelheroes.framework.ui.detail.DetailActivity
import com.angeldearbo.marvelheroes.framework.ui.main.MainViewModel.UiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: HeroesAdapter

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HeroesAdapter(viewModel::onHeroClicked)
        binding.recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {

        binding.progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is UiModel.Content -> adapter.heroes = model.heroes
            is UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.HERO, model.hero.id)
            }
        }
    }
}