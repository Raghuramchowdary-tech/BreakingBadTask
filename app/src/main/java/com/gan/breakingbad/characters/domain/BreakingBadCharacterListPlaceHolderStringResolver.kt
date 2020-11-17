package com.gan.breakingbad.characters.domain

import android.content.Context
import com.gan.breakingbad.R

class BreakingBadCharacterListPlaceHolderStringResolver(val context: Context): BreakingBadStringResolver {
    override fun noResultsTitle(): String {
        return context.resources.getString(R.string.breaking_bad_no_results)
    }

    override fun noResultsSubTitle(): String {
        return context.resources.getString(R.string.breaking_bad_no_results_sub_title)
    }

    override fun noNetworkTitle(): String {
        return context.resources.getString(R.string.no_network)
    }

    override fun noNetworkSubTitle(): String {
        return context.resources.getString(R.string.no_network_sub_title)
    }

    override fun serverErrorSubTitle(): String {
        return context.resources.getString(R.string.server_error)
    }

    override fun serverErrorTitle(): String {
        return context.resources.getString(R.string.server_error_sub_title)
    }

    override fun okButtonText(): String {
        return context.resources.getString(R.string.ok_text)
    }
}


interface BreakingBadStringResolver {
    fun noResultsTitle(): String
    fun noResultsSubTitle(): String
    fun noNetworkTitle(): String
    fun noNetworkSubTitle(): String
    fun okButtonText(): String
    fun serverErrorSubTitle(): String
    fun serverErrorTitle(): String
}