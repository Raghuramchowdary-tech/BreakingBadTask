package com.gan.breakingbad.characters.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BreakingBadCharacter(
   val char_id: Int = 0,
   var name: String = "",
   var birthday: String = "",
   var occupation: List<String>? = listOf(),
   var img: String? = "",
   var status: String? = "",
   var nickname: String? = "",
   var appearance: List<Int>? = listOf(),
   var portrayed: String? = "",
   var category: String? = "",
   var better_call_saul_appearance: List<Int>? = listOf()
): Parcelable


