package com.example.kotlincalculator

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlincalculator.databinding.ActivityNoteBinding

class noteActivity : AppCompatActivity() {
    private val binding by lazy { ActivityNoteBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userEnterInterface()
        goBack()
    }

    private fun goBack(){
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun userEnterInterface(){
        binding.enterNote.setOnClickListener{

        }
    }
}