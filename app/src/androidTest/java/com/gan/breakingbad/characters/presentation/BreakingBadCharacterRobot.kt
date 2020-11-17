package com.gan.breakingbad.characters.presentation

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.gan.breakingbad.R

fun breakingBadCharacterDetails(block: BreakingBadCharacterRobot.() -> Unit) =
    BreakingBadCharacterRobot()

class BreakingBadCharacterRobot {

    fun characterNameIsCorrectlyDisplayed(dogName: String) {
        Espresso.onView(withId(R.id.breakingbad_character_name)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(dogName)

            )
        )
    }

    fun characterPerformedSeasonsIsDisplayedCorrectly(appearance: List<Int>?) {
        Espresso.onView(withId(R.id.breakingbad_character_season_appearance)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    appearance.toString().substring(1, appearance.toString().length - 1)
                )

            )
        )
    }

    fun characterNicknameIsDisplayedCorreclty(nickname: String?) {
        Espresso.onView(withId(R.id.breakingbad_character_nickname)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(nickname)

            )
        )
    }

    fun characterStatusIsDisplayedCorrectly(status: String?) {
        Espresso.onView(withId(R.id.breakingbad_character_status)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(status)

            )
        )
    }

    fun characterOccupationIsDisplayedCorrectly(occupation: List<String>?) {
        Espresso.onView(withId(R.id.breakingbad_character_occupation)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    occupation.toString().substring(1, occupation.toString().length - 1)
                )

            )
        )
    }


}