package com.example.calculator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Percentage : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_percentage)

        val perToMain = findViewById<ImageView>(R.id.back)
        perToMain.setOnClickListener {
            startActivity(Intent(this, Listicons::class.java))
            overridePendingTransition(0, 0)
            finish()
        }

        var percentage = findViewById<EditText>(R.id.percentage)
        var totalMarks = findViewById<EditText>(R.id.totalMarks)
        var calculate = findViewById<Button>(R.id.calculate)
        var result = findViewById<TextView>(R.id.result)


        calculate.setOnClickListener{

            var per =   percentage.text.toString().toDoubleOrNull() ?: 0.0
            var tot =   totalMarks.text.toString().toDoubleOrNull() ?: 0.0

            var operation = (tot * per) / 100;
            result.text = operation.toString();

        }

        var obtain = findViewById<EditText>(R.id.obtain)
        var complete = findViewById<EditText>(R.id.complete)
        var secondCalculate = findViewById<Button>(R.id.secondCalculate)
        var secondResult = findViewById<TextView>(R.id.secondResult)



        secondCalculate.setOnClickListener{

            var ob = obtain.text.toString().toDoubleOrNull() ?: 0.0
            var comp = complete.text.toString().toDoubleOrNull() ?: 0.0


            var secondOperation = (ob / comp) * 100;
            secondResult.text = secondOperation.toString() + "%";

        }






        }
    }
