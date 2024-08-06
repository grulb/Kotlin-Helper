package com.example.kotlincalculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincalculator.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    private val binding by lazy { ActivityNoteBinding.inflate(layoutInflater) }
    private lateinit var db: DBHelper
    private lateinit var notesAdapter : NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = DBHelper(this)
        notesAdapter = NotesAdapter(db.getAllNotes(), this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        newNote()
        goBack()
        setLongPress()
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }

    private fun goBack(){
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun newNote(){
        binding.buttonNewNote.setOnClickListener {
            val intent = Intent(this, EditNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setLongPress(){
        notesAdapter.onItemLongClicked = { position ->
            binding.editNoteButton.visibility = View.VISIBLE
            binding.deleteNoteButton.visibility = View.VISIBLE
            binding.buttonBack.visibility = View.INVISIBLE
            binding.buttonCancel.visibility = View.VISIBLE
        }

        binding.buttonCancel.setOnClickListener {
            binding.editNoteButton.visibility = View.INVISIBLE
            binding.deleteNoteButton.visibility = View.INVISIBLE
            binding.buttonBack.visibility = View.VISIBLE
            binding.buttonCancel.visibility = View.INVISIBLE
        }
    }
}