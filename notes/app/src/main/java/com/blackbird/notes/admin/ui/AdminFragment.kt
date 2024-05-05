package com.blackbird.notes.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.blackbird.notes.admin.data.User
import com.blackbird.notes.admin.data.UserDetails
import com.blackbird.notes.databinding.FragmentAdminBinding
import com.google.firebase.Firebase
import com.google.firebase.database.Exclude
import com.google.firebase.database.database

class AdminFragment :Fragment() {

    private var binding : FragmentAdminBinding? = null
    private var count = 0
    private val adminViewModel: AdminViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAdmin()){
            initializeView()
            getData()
            handleObserver()
        }else{
            binding?.ErrorScreen?.isVisible = true
        }
    }

    private fun initializeView() {
        binding?.apply {
            ErrorScreen.isVisible = false
            adminView.isVisible = true
            clickMe.setOnClickListener {
                firebaseDbTest()
            }
        }
    }


    private fun firebaseDbTest(){
        count +=1
        val author = User(userName = "authorName$count")
        adminViewModel.addUser(author)


    }

    private fun getData(){
        //todo
    }


    private fun handleObserver(){
        adminViewModel.userLiveData.observe(viewLifecycleOwner){
            toastIt("added User ${it.userName}")
            it.id?.let {userName->
                val userDetails = UserDetails(userId = userName, testValue = "${it.userName}testbvale", pay = "25")
                adminViewModel.addUserDetails(userDetails)
            }

        }
    }
    private fun removeObserver(){
        adminViewModel.userLiveData.removeObservers(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        removeObserver()
        super.onDestroyView()
    }

    private fun toastIt(msg:String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    data class Author(
        @Exclude
        var id : String? = null,
        val user : String? = null
    )


    private fun isAdmin():Boolean{
        return true
    }
}