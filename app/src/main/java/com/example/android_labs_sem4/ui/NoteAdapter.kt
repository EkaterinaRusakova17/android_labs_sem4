package com.example.android_labs.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_labs.data.NoteModel
import com.example.android_labs.R

class NoteAdapter(
    private var notes: List<NoteModel> = emptyList(),
    private val onDeleteClick: (NoteModel) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.noteTitleTextView)
        val bodyTextView: TextView = itemView.findViewById(R.id.noteBodyTextView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.bodyTextView.text = note.noteBody
        holder.deleteButton.setOnClickListener { onDeleteClick(note) }
    }

    fun updateNotes(newNotes: List<NoteModel>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}