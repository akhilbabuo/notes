package com.blackbird.notes.common

enum class NoteValidity(msg:String){
    VALID("valid note"),
    NO_TITLE("title missing"),
    NO_BODY("no content in note"),
    INVALID("")
}

object Utils {

    /*
    //check heading and is empty
    //check content is empty
    //
     */
    fun checkValidNote(title:String?, note:String?):NoteValidity{
        return if (title.isNullOrBlank()){
            if (note.isNullOrBlank())
                NoteValidity.INVALID
            else
                NoteValidity.NO_TITLE
        }else{
            if (note.isNullOrBlank()){
                NoteValidity.NO_BODY
            }else{
                NoteValidity.VALID
            }
        }
    }
}