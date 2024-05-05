package com.blackbird.notes.notedetails.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.blackbird.notes.common.ParamsConstant
import com.blackbird.notes.databinding.FragmentNotesUpdateBinding
import com.blackbird.notes.main.data.Notes
import com.blackbird.notes.main.ui.main.MainViewModel


class NotesUpdateFragment : Fragment() {

    private var binding: FragmentNotesUpdateBinding? = null
    private val viewModel : NoteUpdateViewModel by viewModels()
    private val mainViewModel : MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesUpdateBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgument()
        initializeView()
    }

    private fun parseArgument() {
        // get note from previous or create new note
        if (arguments?.containsKey(ParamsConstant.KEY_NOTES_UPDATE) == true) {
            arguments?.let {
                val note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(ParamsConstant.KEY_NOTES_UPDATE, Notes::class.java)
                } else {
                    it.getParcelable(ParamsConstant.KEY_NOTES_UPDATE) as? Notes
                }
                note?.let { mNote ->
                    viewModel.updateCurrentNote(mNote)
                    it.remove(ParamsConstant.KEY_NOTES_UPDATE)
                }
            }
        } else {
            viewModel.updateCurrentNote(Notes(id = mainViewModel.getNextId()))
        }
    }

    private fun initializeView() {
        binding?.apply {
            this.viewmodel = viewModel
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.getCurrentNote()?.let {
            it.title = viewModel.binder.titleCache
            it.message = viewModel.binder.messageCache
            mainViewModel.addNote(it)
        }
        updateNote()
    }

    private fun updateNote(){
        binding?.apply {


        }
    }

}