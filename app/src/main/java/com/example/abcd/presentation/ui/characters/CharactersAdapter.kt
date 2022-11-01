package com.example.abcd.presentation.ui.characters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.abcd.databinding.RecyclerItemBinding
import com.example.abcd.domain.model.Character

class CharacterAdapter(
    context: Context,
    private val userClicked: (com.example.abcd.domain.model.Character) -> Unit
) : ListAdapter<com.example.abcd.domain.model.Character, CharacterViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)
    private var sourcesList = mutableListOf<com.example.abcd.domain.model.Character>()

    fun filter(text: String){
        if(sourcesList.isEmpty()) sourcesList = currentList
        if(!text.isEmpty())submitList(sourcesList.filter { it.name.contains(text) })
        else submitList(sourcesList)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<com.example.abcd.domain.model.Character>() {
            override fun areItemsTheSame(oldItem: com.example.abcd.domain.model.Character, newItem: com.example.abcd.domain.model.Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: com.example.abcd.domain.model.Character, newItem: com.example.abcd.domain.model.Character): Boolean {
                return oldItem.name == newItem.name && oldItem.img == newItem.img
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            binding = RecyclerItemBinding.inflate(layoutInflater, parent, false),
            userClicked = userClicked
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}