package com.blackbird.notes.main.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blackbird.notes.databinding.NotesListLayoutBinding
import com.blackbird.notes.main.data.Notes

class NotesListAdapter(val onClick: (note: Notes) -> Unit) :
    RecyclerView.Adapter<NotesListAdapter.NotesViewHolder>() {

    private var data: MutableList<Notes> = mutableListOf()


    inner class NotesViewHolder(private val binding: NotesListLayoutBinding) :
        ViewHolder(binding.root) {
        fun onBind(notes: Notes, position: Int) {
            binding.apply {
                title.text = notes.title
                message.text = notes.message
                root.setOnClickListener {
                    onClick.invoke(notes)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            NotesListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                null,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.onBind(data[position], position)
    }

    fun setData(list: List<Notes>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
}