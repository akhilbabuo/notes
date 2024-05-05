package com.blackbird.notes.main.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackbird.notes.main.data.Notes
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _noteListLiveData = MutableLiveData(listOf<Notes>())
    val noteListLiveData: LiveData<List<Notes>> get() = _noteListLiveData

    private val cacheNoteList = mutableListOf<Notes>()

    fun getNotesList() {
        viewModelScope.launch {
            _noteListLiveData.postValue(cacheNoteList)
        }
    }

    fun getNextId(): String {
        return (_noteListLiveData.value?.lastOrNull()?.id?.toIntOrNull() ?: (0 + 1)).toString()
    }


    fun createNote(id: String? = null,title:String,message:String){
        addNote(
            Notes(
                id = id ?: getNextId(),
                title = title,
                message = message
            )
        )

    }


    fun addNote(notes: Notes) {
        cacheNoteList.add(notes)
        _noteListLiveData.postValue(cacheNoteList)
    }

    fun removeNote(notes: Notes): Boolean {
        cacheNoteList.firstOrNull { it.id == notes.id }?.let {
            cacheNoteList.remove(it)
            return true
        }
        return false
    }

    fun removeNote(id: String): Boolean {
        cacheNoteList.firstOrNull { it.id == id }?.let {
            cacheNoteList.remove(it)
            return true
        }
        return false
    }
}