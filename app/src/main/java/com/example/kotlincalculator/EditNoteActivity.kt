package com.example.kotlincalculator

import DataClasses.Notes
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.kotlincalculator.databinding.ActivityEditNoteBinding

class EditNoteActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEditNoteBinding.inflate(layoutInflater) }
    private lateinit var db: DBHelper
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
        editNote()
        goBack()
    }

    private fun goBack(){
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun editNote(){
        binding.noteEditName.addTextChangedListener{text ->
            binding.applyNote.isVisible = !text.isNullOrEmpty()
        }

        binding.noteEditDescription.addTextChangedListener{text ->
            binding.applyNote.isVisible = !text.isNullOrEmpty()
        }

        binding.applyNote.setOnClickListener {
            val noteTitle = binding.noteEditName.text.toString()
            val noteDesc = binding.noteEditDescription.text.toString()
            val note = Notes(0, noteTitle, noteDesc)
            db.createNote(note)
            finish()
        }
    }
}