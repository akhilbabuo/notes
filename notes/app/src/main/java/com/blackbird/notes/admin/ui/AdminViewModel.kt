package com.blackbird.notes.admin.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blackbird.notes.admin.FirebaseConstants
import com.blackbird.notes.admin.data.User
import com.blackbird.notes.admin.data.UserDetails
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class AdminViewModel : ViewModel(){
    private val fireDb = Firebase.database(FirebaseConstants.COUNTRY_URL)
    private val _userLiveData = MutableLiveData<User>()
    val userLiveData : LiveData<User> get() = _userLiveData



    fun addUser(user: User){
        val ref = fireDb.getReference(FirebaseConstants.USER_ROUTE)
        ref.push().key?.let { key ->
            user.id = key
            ref.child(key).setValue(user).addOnSuccessListener {
                _userLiveData.postValue(user)
            }.addOnFailureListener {
                Result.failure<Exception>(it)
            }
        }
    }

    fun addUserDetails(userDetails: UserDetails){
        val childId = "${userDetails.userId}_${FirebaseConstants.USER_DETAILS_ROUTE}"
        val ref = fireDb.getReference(childId)
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("tagSnap", "onDataChange: ")
                if (snapshot.exists()){
                    Log.d("tagSnap", "onDataChange: data exist")
                }else{
                    Log.d("tagSnap", "onDataChange: dont exist")
                    ref.child(childId).setValue(userDetails)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //todo
            }

        })
    }


}