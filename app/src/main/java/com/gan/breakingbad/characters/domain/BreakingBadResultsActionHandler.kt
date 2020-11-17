package com.gan.breakingbad.characters.domain

import com.gan.breakingbad.characters.presentation.BreakingBadCharactersListPresenter

internal interface BreakingBadResultsActionHandler {
    fun onErrorButtonClicked(errorState: BreakingBadCharactersListPresenter.ErrorState)
}