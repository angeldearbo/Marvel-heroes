package com.angeldearbo.marvelheroes.framework.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angeldearbo.marvelheroes.R
import com.angeldearbo.marvelheroes.databinding.ViewHeroBinding
import com.angeldearbo.marvelheroes.domain.Hero
import com.angeldearbo.marvelheroes.framework.ui.common.basicDiffUtil
import com.angeldearbo.marvelheroes.framework.ui.common.inflate
import com.angeldearbo.marvelheroes.framework.ui.common.loadUrl

class HeroesAdapter(private val listener: (Hero) -> Unit) :
    RecyclerView.Adapter<HeroesAdapter.ViewHolder>() {

    var heroes: List<Hero> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_hero, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = heroes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hero = heroes[position]
        holder.bind(hero)
        holder.itemView.setOnClickListener { listener(hero) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewHeroBinding.bind(view)
        fun bind(hero: Hero) = with(binding) {
            heroName.text = hero.name
            heroImage.loadUrl(hero.thumbnail)
        }
    }
}