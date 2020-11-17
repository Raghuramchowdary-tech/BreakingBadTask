package com.gan.breakingbad.characters.presentation

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.gan.breakingbad.R
import com.gan.breakingbad.utils.mockCharacter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BreakingBadCharacterFragmentTest {
    private var fragmentScenario: FragmentScenario<BreakingBadCharacterFragment>? = null
    private val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext()
    )

    @Before
    fun setup() {
        navController.setGraph(R.navigation.nav_graph)
    }

    @After
    fun tearDown() {
        fragmentScenario?.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun testIfDetailsAreCorrectlyDisplayed() {
        fragmentScenario =
            launchDogDetailsFragment(Bundle().apply { putParcelable("breakingBadCharacter", mockCharacter) })
        fragmentScenario?.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        breakingBadCharacterDetails {
            characterNameIsCorrectlyDisplayed(mockCharacter.name)
            characterOccupationIsDisplayedCorrectly(mockCharacter.occupation)
            characterStatusIsDisplayedCorrectly(mockCharacter.status)
            characterNicknameIsDisplayedCorreclty(mockCharacter.nickname)
            characterPerformedSeasonsIsDisplayedCorrectly(mockCharacter.appearance)
        }
    }

    private fun launchDogDetailsFragment(args: Bundle = Bundle()): FragmentScenario<BreakingBadCharacterFragment> {
        return launchFragmentInContainer(
            themeResId = R.style.Theme_MaterialComponents,
            fragmentArgs = args
        )
    }

}