package com.gan.breakingbad.characters.domain

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.gan.breakingbad.R
import com.gan.breakingbad.characters.data.BreakingBadCharacter
import com.gan.breakingbad.characters.presentation.BreakinggBadCharactersListFragment
import com.gan.breakingbad.utils.DataBindingIdlingResource
import com.gan.breakingbad.utils.MockBreakingBadCharacterRepository
import com.gan.breakingbad.utils.isGone
import com.gan.breakingbad.utils.mockCharacter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BreakingBadCharactersListFragmentTest {


    private var fragmentScenario: FragmentScenario<BreakinggBadCharactersListFragment>? = null
    private val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext()
    )

    private val mockSearchService =
        MockBreakingBadCharacterRepository()

    private val dataBindingIdlingResourceRule = DataBindingIdlingResource()

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(dataBindingIdlingResourceRule)
        BreakingBadModule.provideBreakingBadRepository(mockSearchService)
        navController.setGraph(R.navigation.nav_graph)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResourceRule)
        fragmentScenario?.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun willDisplayBreakingBadCharcters() {
        val expectedSearchResult = listOf(mockCharacter)
        fragmentScenario = launchDogBreedSearchResults()
        fragmentScenario?.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.breaking_bad_list))
            .isGone()
        mockSearchService.simulateSuccess(expectedSearchResult)
        breakingBadCharacterIsDisplayed(mockCharacter)
    }


    private fun breakingBadCharacterIsDisplayed(mockCharacter: BreakingBadCharacter) {
        Espresso.onView(withText(mockCharacter.name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun launchDogBreedSearchResults(args: Bundle = Bundle()): FragmentScenario<BreakinggBadCharactersListFragment> {
        return launchFragmentInContainer(
            themeResId = R.style.Theme_MaterialComponents,
            fragmentArgs = args
        )
    }


}