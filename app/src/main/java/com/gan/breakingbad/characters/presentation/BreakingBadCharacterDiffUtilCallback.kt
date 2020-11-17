package com.gan.breakingbad.characters.presentation

import androidx.recyclerview.widget.DiffUtil
import com.gan.breakingbad.characters.data.BreakingBadCharacter

class BreakingBadCharacterDiffUtilCallback : DiffUtil.ItemCallback<BreakingBadCharacter>() {

    override fun areItemsTheSame(oldItem: BreakingBadCharacter, newItem: BreakingBadCharacter): Boolean {
        return oldItem.char_id == newItem.char_id
    }

    override fun areContentsTheSame(oldItem: BreakingBadCharacter, newItem: BreakingBadCharacter): Boolean {
        return oldItem.name == newItem.name
    }
}
