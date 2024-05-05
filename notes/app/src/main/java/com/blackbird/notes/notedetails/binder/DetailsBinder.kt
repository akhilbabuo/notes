package com.blackbird.notes.notedetails.binder

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.blackbird.notes.BR

class DetailsBinder : BaseObservable(){

    @get:Bindable
    var titleCache: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleCache)
        }

    @get:Bindable
    var messageCache : String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.messageCache)
        }

}
