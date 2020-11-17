package com.gan.breakingbad.utils

import androidx.fragment.app.Fragment

fun Fragment?.runOnUiThread(action: () -> Unit) {
    this ?: return
    if (!isAdded) return // Fragment not attached to an Activity
    activity?.runOnUiThread(action)
}

fun <K, V> Map<K, V>.toMutableCopy() = HashMap(this)