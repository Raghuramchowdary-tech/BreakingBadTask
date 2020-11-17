package com.gan.breakingbad.utils

import com.gan.breakingbad.characters.data.BreakingBadCharacter
import com.gan.breakingbad.characters.data.BreakingBadCharacterResponse
import com.gan.breakingbad.characters.domain.BreakingBadRepository

class MockBreakingBadCharacterRepository:
    BreakingBadRepository {
    var callback: (BreakingBadCharacterResponse) -> Unit = {}

    fun simulateSuccess(breakingBadCharacterList: List<BreakingBadCharacter>) {
        callback(BreakingBadCharacterResponse.Success(breakingBadCharacterList))
    }

    fun simulateNetworkError() {
        callback(BreakingBadCharacterResponse.NetworkError)
    }

    fun simulateServerError() {
        callback(BreakingBadCharacterResponse.ServerError)
    }

    override fun fetchBreakingBadCharacters(callback: (BreakingBadCharacterResponse) -> Unit) {
        this.callback = callback
    }
}