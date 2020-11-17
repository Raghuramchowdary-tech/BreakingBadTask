package com.gan.breakingbad.characters.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gan.breakingbad.R
import com.gan.breakingbad.characters.data.BreakingBadCharacter
import com.gan.breakingbad.characters.domain.BreakingBadCharacterClickObserver
import com.gan.breakingbad.databinding.BreakingBadCardItemBinding

class BreakingBadCharactersListAdapter(
    private var breakingBadCharacterClickObserver: BreakingBadCharacterClickObserver
) : ListAdapter<BreakingBadCharacter, BreakingBadCharactersListAdapter.ViewHolder>(
    BreakingBadCharacterDiffUtilCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.breaking_bad_card_item, parent, false)
        return ViewHolder(
            view,
            parent.context,
            breakingBadCharacterClickObserver
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        view: View,
        private val context: Context,
        private val breakingBadCharacterClickObserver: BreakingBadCharacterClickObserver
    ) :
        RecyclerView.ViewHolder(view) {
        private val binding = BreakingBadCardItemBinding.bind(view)
        fun bind(breakingBadCharacter: BreakingBadCharacter) {
            with(binding) {
                breakingBadCharacterName.text = breakingBadCharacter.name
                Glide.with(context).load(breakingBadCharacter.img).into(breakingBadImage)
            }
            itemView.setOnClickListener {
                breakingBadCharacterClickObserver.onBreakingBadCharacterClicked(
                    breakingBadCharacter
                )
            }
        }
    }

}