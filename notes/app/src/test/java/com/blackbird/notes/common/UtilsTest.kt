package com.blackbird.notes.common


import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test


class UtilsTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `title and note empty returns invalid`(){
        val result = Utils.checkValidNote(title = "",note = "")
        assertThat(result).isEqualTo(NoteValidity.INVALID)
    }

    @Test
    fun `title empty return no title`(){
        val result = Utils.checkValidNote(title = "",note = "sdfsdffsdf")
        assertThat(result).isEqualTo(NoteValidity.NO_TITLE)
    }

    @Test
    fun `body empty returns no body`(){
        val result = Utils.checkValidNote(title = "dsfds",note = "")
        assertThat(result).isEqualTo(NoteValidity.NO_BODY)
    }

    @Test
    fun `with title and body returns valid`(){
        val result = Utils.checkValidNote(title = "dsfds",note = "asdasd")
        assertThat(result).isEqualTo(NoteValidity.VALID)
    }
}