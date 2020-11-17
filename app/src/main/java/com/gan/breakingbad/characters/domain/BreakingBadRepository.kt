package com.gan.breakingbad.characters.domain
import com.gan.breakingbad.characters.data.BreakingBadCharacterResponse

interface BreakingBadRepository {
    fun fetchBreakingBadCharacters(
        callback: (BreakingBadCharacterResponse) -> Unit
    )
}