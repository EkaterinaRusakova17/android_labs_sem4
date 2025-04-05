package com.example.android_labs.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_labs.data.NoteModel
import com.example.android_labs.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initialiseRecyclerView()
        observeNotesList()
        getNotes()
        setupAddNoteButton()
    }

    private fun initialiseRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.rView)
        adapter = NoteAdapter(onDeleteClick = { noteModel: NoteModel ->
            viewModel.deleteNote(noteModel)
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeNotesList() {
        viewModel.notes.observe(this) { notes ->
            notes?.let {
                adapter.updateNotes(it)
            }
        }
    }

    private fun getNotes() {
        viewModel.loadNotes()
    }

    private fun setupAddNoteButton() {
        findViewById<Button>(R.id.btnGetNotes).setOnClickListener {
            val title = findViewById<EditText>(R.id.titleEditText).text.toString()
            val content = findViewById<EditText>(R.id.contentEditText).text.toString()
            if (title.isNotBlank() && content.isNotBlank()) {
                viewModel.addNote(NoteModel(title = title, noteBody = content))
                clearInputFields()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearInputFields() {
        findViewById<EditText>(R.id.titleEditText).text.clear()
        findViewById<EditText>(R.id.contentEditText).text.clear()
    }
}