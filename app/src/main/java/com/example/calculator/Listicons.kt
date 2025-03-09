package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Listicons : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listicons)

        var calculator = findViewById<TextView>(R.id.calculator)
        var cal = findViewById<ImageView>(R.id.calImg)
        var per = findViewById<ImageView>(R.id.perImg)
        var gpa = findViewById<ImageView>(R.id.gpaImg)
        var discount = findViewById<ImageView>(R.id.tagImg)

        calculator.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }

        cal.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }

        per.setOnClickListener {
            startActivity(Intent(this,Percentage::class.java))
            overridePendingTransition(0, 0)

        }

        gpa.setOnClickListener {
            startActivity(Intent(this,Gpa::class.java))
            overridePendingTransition(0, 0)

        }

        discount.setOnClickListener {
            startActivity(Intent(this,Discount::class.java))
            overridePendingTransition(0, 0)

        }





        }
    }
