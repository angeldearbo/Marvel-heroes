package com.angeldearbo.marvelheroes.framework.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.angeldearbo.marvelheroes.R
import com.angeldearbo.marvelheroes.data.repository.exceptions.AuthorizationError
import com.angeldearbo.marvelheroes.data.repository.exceptions.FatalError
import com.angeldearbo.marvelheroes.data.repository.exceptions.NetworkError
import com.angeldearbo.marvelheroes.data.repository.exceptions.ServerError
import com.angeldearbo.marvelheroes.databinding.ActivityDetailBinding
import com.angeldearbo.marvelheroes.domain.Hero
import com.angeldearbo.marvelheroes.framework.ui.common.loadUrl
import com.angeldearbo.marvelheroes.framework.ui.detail.DetailViewModel.UiModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val HERO = "DetailActivity:hero"
    }

    private val viewModel by viewModels<DetailViewModel>()

    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) = with(binding) {
        when (model) {
            is UiModel.Content -> showDetail(model.hero)
            is UiModel.RetryError -> showRetry(model.throwable)
            is UiModel.Error -> showError(model.throwable)
        }
    }

    private fun showDetail(hero: Hero) {
        binding.heroDetailToolbar.title = hero.name
        binding.heroDetailImage.loadUrl(hero.thumbnail)
        binding.heroDetailSummary.text = hero.description
    }

    private fun showRetry(throwable: Throwable) {
        val msg = when (throwable) {
            is NetworkError -> R.string.netwrk_error
            is ServerError -> R.string.server_error
            else -> R.string.fatal_error
        }

        val snackbar: Snackbar = Snackbar
            .make(binding.root, msg, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.snackbar_retry_msg)) {
                viewModel.findHero()
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
            .make(binding.root, msg, Snackbar.LENGTH_INDEFINITE)
        snackbar.show()
    }
}

