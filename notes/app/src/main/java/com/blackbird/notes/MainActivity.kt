package com.blackbird.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.blackbird.notes.main.data.Notes
import com.blackbird.notes.main.ui.main.MainFragment
import com.blackbird.notes.notedetails.ui.NotesUpdateFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}