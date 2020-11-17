package com.gan.breakingbad.characters.presentation

import androidx.annotation.DrawableRes
import com.gan.breakingbad.R
import com.gan.breakingbad.characters.data.BreakingBadCharacter
import com.gan.breakingbad.characters.data.BreakingBadCharacterResponse
import com.gan.breakingbad.characters.domain.BreakingBadRepository
import com.gan.breakingbad.characters.domain.BreakingBadResultsActionHandler
import com.gan.breakingbad.characters.domain.BreakingBadStringResolver

internal class BreakingBadCharactersListPresenter(
    private val breakingBadRepository: BreakingBadRepository,
    private val stringResolver: BreakingBadStringResolver
) : BreakingBadResultsActionHandler {

    private var viewState: ViewState
    private var viewStateUpdated: ((ViewState) -> Unit)? = null
    private var initialBreakingBadCharactersList = listOf<BreakingBadCharacter>()

    init {
        viewState = ViewState()
        fetchBreakingBadCharacters()
    }


    private fun fetchBreakingBadCharacters() {
        breakingBadRepository.fetchBreakingBadCharacters(::handleBreakingBadCharactersResponse)
    }

    private fun handleBreakingBadCharactersResponse(breakingBadCharacterResponse: BreakingBadCharacterResponse) {

        when (breakingBadCharacterResponse) {
            is BreakingBadCharacterResponse.Success -> {
                if (breakingBadCharacterResponse.breakingBadCharactersList.isNullOrEmpty()) {
                    showNoResultsError()
                } else {
                    initialBreakingBadCharactersList =
                        breakingBadCharacterResponse.breakingBadCharactersList.map { it.copy() }
                    showResults(breakingBadCharacterResponse.breakingBadCharactersList)
                }
            }
            is BreakingBadCharacterResponse.NetworkError -> {
                showNetworkError()
            }
            is BreakingBadCharacterResponse.ServerError -> {
                showServerError()
            }
        }
        viewStateUpdated?.invoke(viewState)
    }

    private fun showResults(results: List<BreakingBadCharacter>) {
        viewState =
            viewState.copy(
                showLoading = false,
                breakingBadCharactersList = results,
                seasonsAppearance = results.flatMap { it.appearance ?: listOf() }.distinct()
                    .sorted(),
                showFilter = false
            )
    }

    private fun showNetworkError() {
        viewState = viewState.copy(
            showLoading = false,
            error = ErrorState.NetworkErrorState(
                title = stringResolver.noNetworkTitle(),
                subtitle = stringResolver.noNetworkSubTitle(),
                buttonText = stringResolver.okButtonText()
            )
        )
    }

    private fun showServerError() {
        viewState = viewState.copy(
            showLoading = false,
            error = ErrorState.ServerErrorState(
                title = stringResolver.serverErrorTitle(),
                subtitle = stringResolver.serverErrorSubTitle(),
                buttonText = stringResolver.okButtonText()
            )
        )
    }

    private fun showNoResultsError() {
        viewState = viewState.copy(
            showLoading = false,
            error = ErrorState.NoResultsState(
                title = stringResolver.noResultsTitle(),
                subtitle = stringResolver.noResultsSubTitle(),
                buttonText = stringResolver.okButtonText()
            )
        )
    }

    override fun onErrorButtonClicked(errorState: ErrorState) {
        viewState = viewState.copy(showLoading = true, error = null)
        viewStateUpdated?.invoke(viewState)
        fetchBreakingBadCharacters()
    }

    fun attachView(viewStateUpdated: (ViewState) -> Unit) {
        this.viewStateUpdated = viewStateUpdated
        viewStateUpdated(viewState)
    }

    fun detachView() {
        viewStateUpdated = null
    }

    fun onFilterTextChanged(queryText: String?) {
        viewState = viewState.copy(
            breakingBadCharactersList = initialBreakingBadCharactersList.filter {
                it.name.contains(
                    queryText ?: "",
                    true
                )
            }
        )
        viewStateUpdated?.invoke(viewState)
    }


    fun filterBySeasonAppearanceClicked(appearance: Int) {
        viewState = viewState.copy(
            breakingBadCharactersList = initialBreakingBadCharactersList.filter { breakingBadCharacter ->
                breakingBadCharacter.appearance?.any { it == appearance } == true
            },
            showFilter = false
        )
        viewStateUpdated?.invoke(viewState)
    }

    fun filterClicked() {
        viewState = viewState.copy(
            showFilter = true
        )
        viewStateUpdated?.invoke(viewState)
    }


    data class ViewState(
        val showLoading: Boolean = true,
        val breakingBadCharactersList: List<BreakingBadCharacter>? = null,
        val error: ErrorState? = null,
        val seasonsAppearance: List<Int>? = null,
        val showFilter: Boolean = false
    )

    sealed class ErrorState(
        val title: String = "",
        val subtitle: String = "",
        val buttonText: String = "",
        @DrawableRes val errorIcon: Int = R.drawable.ic_baseline_error_24,
        val showError: Boolean = false
    ) {
        class NoResultsState(title: String, subtitle: String, buttonText: String) :
            ErrorState(title, subtitle, buttonText, R.drawable.ic_baseline_error_24, true)

        class NetworkErrorState(title: String, subtitle: String, buttonText: String) :
            ErrorState(title, subtitle, buttonText, R.drawable.ic_baseline_error_24, true)

        class ServerErrorState(title: String, subtitle: String, buttonText: String) :
            ErrorState(title, subtitle, buttonText, R.drawable.ic_baseline_error_24, true)

    }


}