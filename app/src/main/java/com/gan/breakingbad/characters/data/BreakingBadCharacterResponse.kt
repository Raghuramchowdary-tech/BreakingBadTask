package com.gan.breakingbad.characters.data

sealed class BreakingBadCharacterResponse {
    data class Success(val breakingBadCharactersList: List<BreakingBadCharacter>): BreakingBadCharacterResponse()
    object NetworkError : BreakingBadCharacterResponse()
    object ServerError : BreakingBadCharacterResponse()
}
