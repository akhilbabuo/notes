package com.blackbird.notes.admin.data

import com.google.firebase.database.Exclude

data class User(
    @Exclude
    var id : String? = null,
    val userName : String? = null
)

data class UserDetails(
    var userId : String,
    val testValue : String,
    val pay : String
)
