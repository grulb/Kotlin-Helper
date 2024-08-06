package com.example.kotlincalculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlincalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var userNumber = ""
    private var editedNumberFirst: Double = 0.000
    private var editedNumberSecond: Double = 0.000
    private var result: Double = 0.000
    private var currentOperation: String? = null
    private var expectingSecondNumber: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        numbersTouch()
        windowSwitch()
    }

    private fun numbersTouch(){
        binding.buttonZero.setOnClickListener {
            appendNumber("0")
        }

        binding.buttonOne.setOnClickListener {
            appendNumber("1")
        }

        binding.buttonTwo.setOnClickListener {
            appendNumber("2")
        }

        binding.buttonThree.setOnClickListener {
            appendNumber("3")
        }

        binding.buttonFour.setOnClickListener {
            appendNumber("4")
        }

        binding.buttonFive.setOnClickListener {
            appendNumber("5")
        }

        binding.buttonSix.setOnClickListener {
            appendNumber("6")
        }

        binding.buttonSeven.setOnClickListener {
            appendNumber("7")
        }

        binding.buttonEight.setOnClickListener {
            appendNumber("8")
        }

        binding.buttonNine.setOnClickListener {
            appendNumber("9")
        }

        binding.buttonPlus.setOnClickListener {
            prepareForOperation("+")
        }

        binding.buttonMinus.setOnClickListener {
            prepareForOperation("-")
        }

        binding.buttonMultiply.setOnClickListener {
            prepareForOperation("*")
        }

        binding.buttonDivide.setOnClickListener {
            prepareForOperation("÷")
        }

        binding.buttonPercent.setOnClickListener {
            prepareForOperation("%")
        }

        binding.buttonClearOne.setOnClickListener {
            if (userNumber.isNotEmpty()) {
                userNumber = userNumber.dropLast(1)
                binding.textViewNumbers.setText(userNumber)
            }
        }

        binding.buttonClearAll.setOnClickListener {
            userNumber = ""
            editedNumberFirst = 0.000
            editedNumberSecond = 0.000
            currentOperation = null
            expectingSecondNumber = false
            binding.textViewEquation.text = ""
            binding.textViewNumbers.text = ""
        }

        binding.buttonEqualy.setOnClickListener {
            if (expectingSecondNumber) {
                editedNumberSecond = userNumber.toDouble()
                when (currentOperation) {
                    "+" -> result = editedNumberFirst + editedNumberSecond
                    "-" -> result = editedNumberFirst - editedNumberSecond
                    "*" -> result = editedNumberFirst * editedNumberSecond
                    "÷" -> result = editedNumberFirst / editedNumberSecond
                    "%" -> result = (editedNumberFirst / 100) * editedNumberSecond
                }
                binding.textViewEquation.setText(result.toString())
                userNumber = result.toString()
                binding.textViewNumbers.text = ""
                currentOperation = null
                expectingSecondNumber = false
            }
        }
    }

    private fun appendNumber(number: String) {
        userNumber += number
        binding.textViewNumbers.setText(userNumber)
    }

    private fun prepareForOperation(operation: String) {
        if (userNumber.isNotEmpty()) {
            editedNumberFirst = userNumber.toDouble()
            currentOperation = operation
            binding.textViewEquation.text = "$editedNumberFirst $currentOperation"
            userNumber = ""
            expectingSecondNumber = true
        }
    }

    private fun windowSwitch(){
        binding.notebookLight.setOnClickListener{
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }

        binding.AILightIcon.setOnClickListener{
            val intent = Intent(this, NeuroActivity::class.java)
            startActivity(intent)
        }
    }
}