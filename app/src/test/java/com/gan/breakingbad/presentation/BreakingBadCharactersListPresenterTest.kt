package com.gan.breakingbad.presentation

import com.gan.breakingbad.characters.domain.BreakingBadStringResolver
import com.gan.breakingbad.characters.presentation.BreakingBadCharactersListPresenter
import com.gan.breakingbad.domain.MockBreakingBadCharacterRepository
import com.gan.breakingbad.utils.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotSame
import org.junit.Test

class BreakingBadCharactersListPresenterTest {

    private val mockCharactersList = listOf(mockBreakingBadCharacter)

    private val noResultsTitle = "NoResult-Title"
    private val noResultsSubtitle = "NoResult-Subtitle"

    private val networkErrorTitle = "NetworkError-Title"
    private val networkErrorSubtitle = "NetworkError-Subtitle"
    private val errorButtonText = "Error-Button"

    private val serverErrorTitle = "ServerError-Title"
    private val serverErrorSubtitle = "ServerError-Subtitle"

    private lateinit var viewState: BreakingBadCharactersListPresenter.ViewState

    private val breakingBadCharacterService = MockBreakingBadCharacterRepository()


    private val presenter = BreakingBadCharactersListPresenter(
        breakingBadCharacterService,
        StubSearchResultsStringResolver(
            noResultsTitle, noResultsSubtitle,
            networkErrorTitle, networkErrorSubtitle, errorButtonText,
            serverErrorTitle, serverErrorSubtitle
        )
    )


    @Test
    fun `when request is in progress then loading state is emitted`() {
        // Given
        val expectedViewState = BreakingBadCharactersListPresenter.ViewState(
            showLoading = true
        )
        attachView()

        // When
        attachView()
        // Then
        assertEquals(expectedViewState, viewState)
    }

    @Test
    fun `when request is finished then loading state is not emitted`() {
        // Given
        val expectedViewState = BreakingBadCharactersListPresenter.ViewState(
            showLoading = false
        )
        attachView()

        //When
        breakingBadCharacterService.simulateSuccess(mockCharactersList)

        // Then
        assertEquals(expectedViewState.showLoading, viewState.showLoading)
    }


    @Test
    fun `when request is successful then success state is emitted`() {
        val expectedViewState = BreakingBadCharactersListPresenter.ViewState(
            showLoading = false,
            breakingBadCharactersList = mockCharactersList,
            showFilter = false,
            seasonsAppearance = listOf(1,2,3,4,5)
        )
        attachView()

        //When
        breakingBadCharacterService.simulateSuccess(mockCharactersList)

        // Then
        assertEquals(expectedViewState, viewState)
    }

    @Test
    fun `when request is successful with empty response then error state is emitted`() {
        val expectedViewState = BreakingBadCharactersListPresenter.ViewState(
            showLoading = false,
            breakingBadCharactersList = emptyList(),
            error = BreakingBadCharactersListPresenter.ErrorState.ServerErrorState(
                title = noResultsTitle,
                subtitle = noResultsSubtitle,
                buttonText = errorButtonText
            )

        )
        attachView()

        //When
        breakingBadCharacterService.simulateSuccess(emptyList())

        // Then
        assertEquals(true, viewState.error!!.showError)
        assertEquals(expectedViewState.error!!.title, viewState.error!!.title)
        assertEquals(expectedViewState.error!!.subtitle, viewState.error!!.subtitle)
        assertEquals(expectedViewState.error!!.buttonText, viewState.error!!.buttonText)
    }

    @Test
    fun `when request is successful the viewstate has correct data`() {
        val expectedViewState = BreakingBadCharactersListPresenter.ViewState(
            showLoading = false,
            breakingBadCharactersList = mockCharactersList

        )
        attachView()

        //When
        breakingBadCharacterService.simulateSuccess(mockCharactersList)

        // Then
        assertEquals(expectedViewState.breakingBadCharactersList, viewState.breakingBadCharactersList)
    }

    @Test
    fun `when filtered with text 'Jr' walter white junior result is published`() {
        val characterslist = listOf(mockBreakingBadCharacter, mockBreakingBadCharacter2)
        val expectedViewState = BreakingBadCharactersListPresenter.ViewState(
            showLoading = false,
            breakingBadCharactersList = listOf(mockBreakingBadCharacter2)

        )
        attachView()

        //When
        breakingBadCharacterService.simulateSuccess(characterslist)
        presenter.onFilterTextChanged("Jr")

        // Then
        assertEquals(expectedViewState.breakingBadCharactersList, viewState.breakingBadCharactersList)
    }

    @Test
    fun `when filtered with season one then only characters of season 1 are published`() {
        val characterslist = listOf(mockBreakingBadCharacter, mockBreakingBadCharacter2, mockBreakingBadCharacterSeason1AndSeason2, mockBreakingBadCharacterSeason3)
        val expectedViewState = BreakingBadCharactersListPresenter.ViewState(
            showLoading = false,
            breakingBadCharactersList = listOf(mockBreakingBadCharacter, mockBreakingBadCharacter2, mockBreakingBadCharacterSeason1AndSeason2)

        )
        attachView()

        //When
        breakingBadCharacterService.simulateSuccess(characterslist)
        presenter.filterBySeasonAppearanceClicked(1)

        // Then
        assertEquals(expectedViewState.breakingBadCharactersList, viewState.breakingBadCharactersList)
        assertNotSame(characterslist, viewState.breakingBadCharactersList)
    }

    @Test
    fun `when filtered with text that doesn't match any name then empty list is published`() {
        val characterslist = listOf(mockBreakingBadCharacter, mockBreakingBadCharacter2)
        val expectedViewState = BreakingBadCharactersListPresenter.ViewState(
            showLoading = false,
            breakingBadCharactersList = emptyList()
        )
        attachView()

        //When
        breakingBadCharacterService.simulateSuccess(characterslist)
        presenter.onFilterTextChanged("ss")

        //Then
        assertNotSame(expectedViewState.breakingBadCharactersList, viewState.breakingBadCharactersList)
    }


    @Test
    fun `when request is failure with server error then error state is emitted`() {
        val expectedViewState = BreakingBadCharactersListPresenter.ViewState(
            showLoading = false,
            error = BreakingBadCharactersListPresenter.ErrorState.ServerErrorState(
                title = serverErrorTitle,
                subtitle = serverErrorSubtitle,
                buttonText = errorButtonText
            )

        )
        attachView()

        //When
        breakingBadCharacterService.simulateServerError()

        // Then
        assertEquals(expectedViewState.error!!.showError, viewState.error!!.showError)
        assertEquals(expectedViewState.error.title, viewState.error!!.title)
        assertEquals(expectedViewState.error.subtitle, viewState.error!!.subtitle)
        assertEquals(expectedViewState.error.buttonText, viewState.error!!.buttonText)
    }

    @Test
    fun `when request is failure with network error then error state is emitted`() {
        val expectedViewState = BreakingBadCharactersListPresenter.ViewState(
            showLoading = false,
            error = BreakingBadCharactersListPresenter.ErrorState.ServerErrorState(
                title = networkErrorTitle,
                subtitle = networkErrorSubtitle,
                buttonText = errorButtonText
            )

        )
        attachView()

        //When
        breakingBadCharacterService.simulateNetworkError()

        // Then
        assertEquals(expectedViewState.error!!.showError, viewState.error!!.showError)
        assertEquals(expectedViewState.error.title, viewState.error!!.title)
        assertEquals(expectedViewState.error.subtitle, viewState.error!!.subtitle)
        assertEquals(expectedViewState.error.buttonText, viewState.error!!.buttonText)
    }

    private fun attachView() {
        presenter.attachView {
            viewState = it
        }
    }

    class StubSearchResultsStringResolver(
        private val noResultsTitle: String,
        private val noResultsSubtitle: String,
        private val networkErrorTitle: String,
        private val networkErrorSubtitle: String,
        private val errorButtonText: String,
        private val serverErrorTitle: String,
        private val serverErrorSubtitle: String
    ) : BreakingBadStringResolver {
        override fun noResultsTitle(): String {
            return noResultsTitle
        }

        override fun noResultsSubTitle(): String {
            return noResultsSubtitle
        }

        override fun noNetworkTitle(): String {
            return networkErrorTitle
        }

        override fun noNetworkSubTitle(): String {
            return networkErrorSubtitle
        }

        override fun okButtonText(): String {
            return errorButtonText
        }

        override fun serverErrorSubTitle(): String {
            return serverErrorSubtitle
        }

        override fun serverErrorTitle(): String {
            return serverErrorTitle
        }
    }


}