package com.angeldearbo.marvelheroes.framework.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.angeldearbo.marvelheroes.R
import com.angeldearbo.marvelheroes.data.repository.exceptions.AuthorizationError
import com.angeldearbo.marvelheroes.data.repository.exceptions.FatalError
import com.angeldearbo.marvelheroes.data.repository.exceptions.NetworkError
import com.angeldearbo.marvelheroes.data.repository.exceptions.ServerError
import com.angeldearbo.marvelheroes.databinding.ActivityMainBinding
import com.angeldearbo.marvelheroes.framework.ui.common.startActivity
import com.angeldearbo.marvelheroes.framework.ui.detail.DetailActivity
import com.angeldearbo.marvelheroes.framework.ui.main.MainViewModel.UiModel
import com.google.android.material.snackbar.Snackbar
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
            is UiModel.RetryError -> showRetry(model.throwable)
            is UiModel.Error -> showError(model.throwable)
        }
    }

    private fun showRetry(throwable: Throwable) {
        val msg = when (throwable) {
            is NetworkError -> R.string.netwrk_error
            is ServerError -> R.string.server_error
            else -> R.string.fatal_error
        }

        val snackbar: Snackbar = Snackbar
            .make(binding.recycler, msg, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.snackbar_retry_msg)) {
                viewModel.onCreate()
            }
        snackbar.show()
    }

    private fun showError(throwable: Throwable) {
        val msg = when (throwable) {
            is AuthorizationError -> R.string.authorization_error
            is FatalError -> R.string.fatal_error
            else -> R.string.fatal_error
        }
        val snackbar: Snackbar = Snackbar
            .make(binding.recycler, msg, Snackbar.LENGTH_INDEFINITE)
        snackbar.show()
    }
}