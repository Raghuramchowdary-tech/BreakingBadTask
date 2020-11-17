package com.gan.breakingbad.utils

import com.gan.breakingbad.characters.data.BreakingBadCharacter

val mockCharacter = BreakingBadCharacter(
    char_id = 1,
    name = "Walter White",
    birthday = "09-07-1958",
    occupation = listOf("High School Chemistry Teacher",
        "Meth King Pin"),
    img = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
    status = "Deceased",
    nickname = "Heisenberg",
    appearance = listOf(1, 2, 3, 4, 5),
    portrayed = "Bryan Cranston",
    category = "",
    better_call_saul_appearance = listOf()
)

val mockBreakingBadCharacterJr = BreakingBadCharacter(
    char_id = 4,
    name = "Walter White Jr",
    birthday = "07-08-1993",
    occupation = listOf("Teenager"),
    img = "https://media1.popsugar-assets.com/files/thumbor/WeLUSvbA.jpg",
    status = "Alive",
    nickname = "Flynn",
    appearance = listOf(1, 2, 3, 4, 5),
    portrayed = "RJ Mitte",
    category = "Breaking Bad",
    better_call_saul_appearance = listOf()
)

val mockBreakingBadCharacterOnlySeason3 = BreakingBadCharacter(
    char_id = 5,
    name = "Test Name",
    birthday = "07-08-1973",
    occupation = listOf("Professor"),
    img = "https://media1.popsugar-assets.com/files/thumbor/WeLUSvbA.jpg",
    status = "Alive",
    nickname = "John",
    appearance = listOf(3),
    portrayed = "Rend",
    category = "Breaking Bad",
    better_call_saul_appearance = listOf()
)

val mockBreakingBadCharacterBothSeason1AndSeason2 = BreakingBadCharacter(
    char_id = 5,
    name = "Test Name",
    birthday = "07-08-1973",
    occupation = listOf("Professor"),
    img = "https://media1.popsugar-assets.com/files/thumbor/WeLUSvbA.jpg",
    status = "Alive",
    nickname = "John",
    appearance = listOf(1,2),
    portrayed = "Rend",
    category = "Breaking Bad",
    better_call_saul_appearance = listOf()
)

