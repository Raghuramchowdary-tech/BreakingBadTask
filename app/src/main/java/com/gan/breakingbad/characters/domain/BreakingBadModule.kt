package com.gan.breakingbad.characters.domain

import androidx.annotation.VisibleForTesting
import com.gan.breakingbad.characters.data.RetrofitBreakingBadRepository


object BreakingBadModule {
    private const val BASE_URL = "https://breakingbadapi.com/api/"

    internal lateinit var breakingBadRepository: BreakingBadRepository

    private var shouldInitializeRepository = true

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun provideBreakingBadRepository(breakingBadRepository: BreakingBadRepository) {
        BreakingBadModule.breakingBadRepository = breakingBadRepository
        shouldInitializeRepository = false
    }

    init {
        if (shouldInitializeRepository) breakingBadRepository =
            RetrofitBreakingBadRepository(BASE_URL)
    }
}