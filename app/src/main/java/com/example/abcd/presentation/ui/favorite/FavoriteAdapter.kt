package com.example.abcd.presentation.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.abcd.data.di.useCaseModule
import com.example.abcd.databinding.RecyclerItemBinding
import com.example.abcd.domain.model.Character

class FavoriteAdapter(
    context: Context,
    private val userClicked: (Character) -> Unit
) : ListAdapter<Character, FavoriteViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.name == newItem.name && oldItem.img == newItem.img
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            binding = RecyclerItemBinding.inflate(layoutInflater, parent, false),
            userClicked = userClicked
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}