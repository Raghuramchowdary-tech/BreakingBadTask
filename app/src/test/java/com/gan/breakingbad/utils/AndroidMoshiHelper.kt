package com.gan.breakingbad.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

inline fun <reified T> createMoshiAdapter(vararg adapters: JsonAdapter.Factory): JsonAdapter<T> {
    val moshi = Moshi.Builder().run {
        adapters.forEach { add(it) }
        add(KotlinJsonAdapterFactory())
        build()
    }

    return moshi.adapter(T::class.java)!!
}

inline fun <reified T> toJson(value: T, vararg adapters: JsonAdapter.Factory) =
    createMoshiAdapter<T>(*adapters).toJson(value)!!

inline fun <reified T> fromJson(jsonString: String, vararg adapters: JsonAdapter.Factory): T =
    createMoshiAdapter<T>(*adapters).fromJson(jsonString)!!