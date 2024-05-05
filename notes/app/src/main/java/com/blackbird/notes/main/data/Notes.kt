package com.blackbird.notes.main.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Notes(
    val id: String,
    var title: String = "",
    var message: String = ""
) : Parcelable