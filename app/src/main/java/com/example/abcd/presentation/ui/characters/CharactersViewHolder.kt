package com.example.abcd.presentation.ui.characters

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.abcd.databinding.RecyclerItemBinding
import com.example.abcd.domain.model.Character
import com.example.abcd.presentation.ext.loadImageUrl

class CharacterViewHolder(
    private val binding: RecyclerItemBinding,
    private val userClicked: (Character) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character) {
        with(binding) {
            characterIcon.loadImageUrl(character.img)
            characterName.text = character.name
            characterNickName.text = character.characterInfo.nickname
            characterPortrayed.text = character.characterInfo.portrayed

            root.setOnClickListener {
                userClicked(character)
            }
        }
    }
}