package com.blackbird.notes.main.ui.main

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blackbird.notes.R
import com.blackbird.notes.common.ParamsConstant
import com.blackbird.notes.databinding.FragmentMainBinding
import com.blackbird.notes.main.data.Notes
import com.blackbird.notes.main.ui.adapter.NotesListAdapter
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.messaging.FirebaseMessaging
import java.util.jar.Manifest

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private var adapter: NotesListAdapter? = null
    private val firebase = Firebase.analytics


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        askNotificationPermission()
        getToken()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotesList()
        handleObserver()
    }

    private fun handleObserver() {
        viewModel.noteListLiveData.observe(viewLifecycleOwner) {
            binding?.isDataAvailable = it.isNotEmpty()
            if (it.isNotEmpty()) {
                adapter?.setData(it)
            }
        }
    }

    private fun initializeView() {
        binding?.apply {
            adapter = NotesListAdapter() {
                addOrUpdateNote(it)
            }
            listRecycleView.adapter = adapter

            addNote.setOnClickListener {
                addOrUpdateNote()
            }
            addNoteSecondary.setOnClickListener {
                addOrUpdateNote()
            }
            idButton.setOnLongClickListener {
                moveToAdmin()
                return@setOnLongClickListener true
            }
        }

    }

    private fun moveToAdmin() {
        findNavController().navigate(R.id.action_mainFragment_to_adminFragment, bundleOf())
    }

    override fun onPause() {
        super.onPause()
        removeObserver()
    }

    private fun removeObserver() {
        viewModel.noteListLiveData.removeObservers(viewLifecycleOwner)
    }

    private fun addOrUpdateNote(notes: Notes? = null) {
        firebase.logEvent("addOrUpdateNote", bundleOf("tesing" to "value"))
        val bundle = Bundle()
        if (notes != null)
            bundle.putParcelable(ParamsConstant.KEY_NOTES_UPDATE, notes)
        findNavController().navigate(R.id.action_mainFragment_to_notesUpdateFragment, bundle)
    }

    //notification

    private fun getToken(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("token", "getToken: $it")
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.

        }
    }

    private fun askNotificationPermission() {
        val ctx = context ?: return
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(ctx, POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(POST_NOTIFICATIONS)
            }
        }
    }

}