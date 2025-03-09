package com.example.calculator

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Discount : ComponentActivity() {
    private var currentFocusedEditText: EditText? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_discount)



        val backButton = findViewById<ImageView>(R.id.back)

        backButton.setOnClickListener {

            startActivity(Intent(this, Listicons::class.java))
            finish()
        }


        var btnBack = findViewById<ImageView>(R.id.btnBack)
        var btnClear = findViewById<Button>(R.id.btnClear)

        var ogPrice = findViewById<EditText>(R.id.originalPrice)
        var dcPrice = findViewById<EditText>(R.id.discountPrice)
        var fnPrice = findViewById<TextView>(R.id.finalPrice)
        var saveValue = findViewById<TextView>(R.id.saveValue)

        ogPrice.showSoftInputOnFocus = false
        dcPrice.showSoftInputOnFocus = false
        ogPrice.isCursorVisible = false
        dcPrice.isCursorVisible = false


        // Handle focus changes to track the currently focused EditText
        val focusChangeListener = { editText: EditText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    currentFocusedEditText = editText
                    editText.setTextColor(Color.parseColor("#FF8200"))
                    if (editText.text.toString() == "0") {
                        editText.text.clear()
                    }
                    editText.isCursorVisible = true
                } else {
                    if (editText.text.toString().isEmpty()) {
                        editText.setText("0")
                    }
                    editText.setTextColor(Color.BLACK)
                }
            }
        }

        focusChangeListener(ogPrice)
        focusChangeListener(dcPrice)


//        ogPrice.setOnFocusChangeListener { _, hasFocus ->
//            if (hasFocus) {
//                ogPrice.setTextColor(Color.parseColor("#FF8200"))
//                ogPrice.text.clear()
//                ogPrice.isCursorVisible = true
//            } else if (!hasFocus && ogPrice.text.toString().isEmpty()) {
//                ogPrice.setTextColor(Color.BLACK) // Black when focus is lost
//                ogPrice.setText("0")
//            }
//        }

//        dcPrice.setOnFocusChangeListener { _, hasFocus ->
//            if (hasFocus) {
//                dcPrice.setTextColor(Color.parseColor("#FF8200"))
//                dcPrice.text.clear()
//                dcPrice.isCursorVisible = true
//            } else if (!hasFocus && ogPrice.text.toString().isEmpty()) {
//                dcPrice.setTextColor(Color.BLACK) // Black when focus is lost
//                dcPrice.setText("0")
//            }
//        }


        // Find the remove button

        btnBack.setOnClickListener {
            currentFocusedEditText?.let { editText ->
                val text = editText.text.toString()
                if (text.isNotEmpty()) {
                    editText.setText(text.substring(0, text.length - 1)) // Remove last character
                    editText.setSelection(editText.text.length) // Move cursor to the end
                }
            }
        }

        btnClear.setOnClickListener {
            ogPrice.setText("0")
            dcPrice.setText("0")
            fnPrice.setText("0")
            saveValue.text = "0"
        }


        // Append number to the currently focused EditText
        val numberButtons = listOf(
            R.id.btnZero to "0",
            R.id.btnDoubleZero to "00",
            R.id.btnOne to "1",
            R.id.btnTwo to "2",
            R.id.btnThree to "3",
            R.id.btnFour to "4",
            R.id.btnFive to "5",
            R.id.btnSix to "6",
            R.id.btnSeven to "7",
            R.id.btnEight to "8",
            R.id.btnNine to "9",
            R.id.btnDecimal to "."
        )

        for ((buttonId, value) in numberButtons) {
            findViewById<Button>(buttonId).setOnClickListener {
                currentFocusedEditText?.append(value)
            }
        }


        // Function to perform instant calculation
        fun calculateDiscount() {

            val originalPrice = ogPrice.text.toString().toDoubleOrNull() ?: 0.0
            val discountPrice = dcPrice.text.toString().toDoubleOrNull() ?: 0.0

            val discountAmount = (originalPrice * discountPrice) / 100
            val finalValue = originalPrice - discountAmount

                fnPrice.text = String.format("%.2f", finalValue);
                saveValue.text = String.format("%.2f", discountAmount)
            }




        // Attach TextWatcher to both EditTexts

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateDiscount()
            }
        }

        ogPrice.addTextChangedListener(textWatcher)
        dcPrice.addTextChangedListener(textWatcher)


//        btnBack.setOnClickListener {
//
//            val originalPrice = ogPrice.text.toString()
//            val discountPrice = dcPrice.text.toString()
//
//            if (originalPrice.isEmpty() || discountPrice.isEmpty()) {
//                Toast.makeText(this, "Please enter values", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            else {
//                val originalPriceValue = originalPrice.toDoubleOrNull() ?:0.0
//                val discountPercentageValue = discountPrice.toDoubleOrNull() ?:0.0
//
//                val discountAmount = (originalPriceValue * discountPercentageValue) / 100
//                val finalValue = originalPriceValue - discountAmount
//
//                fnPrice.text = String.format("%.2f", finalValue);
//                saveValue.text = String.format("%.2f" , discountAmount)
//
//
//            }
//
//
//        }








    }
}