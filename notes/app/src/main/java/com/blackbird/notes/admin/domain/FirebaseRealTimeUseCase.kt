package com.blackbird.notes.admin.domain

import com.blackbird.notes.admin.FirebaseConstants
import com.blackbird.notes.admin.FirebaseConstants.USER_ROUTE
import com.blackbird.notes.admin.data.User
import com.blackbird.notes.admin.data.UserDetails
import com.google.firebase.Firebase
import com.google.firebase.database.database


interface IFirebaseRealTimeUseCase {
    fun addUser(user: User): Result<String>
    fun getUserId(userName: String): Result<String>
    fun addUserDetails(user: User): Result<UserDetails>

}

//class FirebaseRealTimeUseCase : IFirebaseRealTimeUseCase {
//
//    override
//
//    override fun getUserId(userName: String): Result<String> {
//        TODO("Not yet implemented")
//    }
//
//    override fun addUserDetails(user: User): Result<UserDetails> {
//        TODO("Not yet implemented")
//    }
//
//
//}