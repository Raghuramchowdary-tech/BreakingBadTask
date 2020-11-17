package com.gan.breakingbad.characters.domain

import androidx.navigation.fragment.FragmentNavigator
import com.gan.breakingbad.characters.data.BreakingBadCharacter

interface BreakingBadCharacterClickObserver {
    fun onBreakingBadCharacterClicked(
        breakingBarCharacter: BreakingBadCharacter,
    )
}