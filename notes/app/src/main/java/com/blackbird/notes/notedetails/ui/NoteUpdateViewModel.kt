package com.blackbird.notes.notedetails.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blackbird.notes.main.data.Notes
import com.blackbird.notes.notedetails.binder.DetailsBinder

class NoteUpdateViewModel : ViewModel() {

    private var _noteLiveData : MutableLiveData<Notes?> = MutableLiveData(null)
    val notesLiveData : LiveData<Notes?> get() = _noteLiveData
    private var _currentNote :Notes? = null

    val binder = DetailsBinder()




    fun updateCurrentNote(notes: Notes){
        if (_currentNote != null){
            _currentNote?.message = notes.message
            _currentNote?.title = notes.title
            _noteLiveData.postValue(_currentNote)
        }else{
            _currentNote = notes
            _noteLiveData.postValue(_currentNote)
        }
        updateLocalText(notes.title,notes.message)
    }


    fun updateText(title:String,message:String){
        if (_currentNote != null){
            _currentNote?.apply {
                this.title = title
                this.message = message
            }
            _noteLiveData.postValue(_currentNote)
        }
        updateLocalText(title, message)
    }

    private fun updateLocalText(title:String,message:String){
        binder.titleCache = title
        binder.messageCache = message
    }

    fun getCurrentNote() : Notes?{
        return _currentNote
    }

}