package com.example.calculator

import android.annotation.SuppressLint
import android.content.Intent
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

class Gpa : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gpa)

        var back = findViewById<ImageView>(R.id.btnBack)

        back.setOnClickListener {
            startActivity(Intent(this,Listicons::class.java))
            overridePendingTransition(0, 0)
            finish()
        }


        var web_score = findViewById<EditText>(R.id.web)

        var web_grade = findViewById<TextView>(R.id.web_grade)
        var web_grade_point = findViewById<TextView>(R.id.web_grade_point)

        var aa_score = findViewById<EditText>(R.id.aa)
        var aa_grade = findViewById<TextView>(R.id.aa_grade)
        var aa_grade_point = findViewById<TextView>(R.id.aa_grade_point)

        var cc_score = findViewById<EditText>(R.id.cc)
        var cc_grade = findViewById<TextView>(R.id.cc_grade)
        var cc_grade_point = findViewById<TextView>(R.id.cc_grade_point)

        var iti_score = findViewById<EditText>(R.id.iti)
        var iti_grade = findViewById<TextView>(R.id.iti_grade)
        var iti_grade_point = findViewById<TextView>(R.id.iti_grade_point)

        var ns_score = findViewById<EditText>(R.id.ns)
        var ns_grade = findViewById<TextView>(R.id.ns_grade)
        var ns_grade_point = findViewById<TextView>(R.id.ns_grade_point)

        var mad_score = findViewById<EditText>(R.id.mad)
        var mad_grade = findViewById<TextView>(R.id.mad_grade)
        var mad_grade_point = findViewById<TextView>(R.id.mad_grade_point)

        var gpa = findViewById<TextView>(R.id.gpa)
        var final_grade = findViewById<TextView>(R.id.final_grade)
        var final_percentage = findViewById<TextView>(R.id.final_percentage)
        var final_status = findViewById<TextView>(R.id.final_status)


        var clear = findViewById<Button>(R.id.clear_btn)
        var calculate = findViewById<Button>(R.id.calc_btn)

        clear.setOnClickListener {

            mad_score.text.clear()
            web_score.text.clear()
            aa_score.text.clear()
            cc_score.text.clear()
            iti_score.text.clear()
            ns_score.text.clear()

            web_grade.text = ""
            aa_grade.text = ""
            cc_grade.text = ""
            iti_grade.text = ""
            ns_grade.text = ""
            mad_grade.text = ""

            web_grade_point.text = ""
            aa_grade_point.text = ""
            cc_grade_point.text = ""
            iti_grade_point.text = ""
            ns_grade_point.text = ""
            mad_grade_point.text = ""

            gpa.text = ""
            final_grade.text = ""
            final_percentage.text = ""
            final_status.text = ""

            Toast.makeText(this, "All fields cleared", Toast.LENGTH_SHORT).show()

        }


        setupTextWatcher(web_score, web_grade, web_grade_point)
        setupTextWatcher(aa_score, aa_grade, aa_grade_point)
        setupTextWatcher(cc_score, cc_grade, cc_grade_point)
        setupTextWatcher(iti_score, iti_grade, iti_grade_point)
        setupTextWatcher(ns_score, ns_grade, ns_grade_point)
        setupTextWatcher(mad_score, mad_grade, mad_grade_point)


        //calculating sum of all grade points of course

        calculate.setOnClickListener {

            var web_grade_point_value = web_grade_point.text.toString().toDoubleOrNull() ?: 0.0
            var aa_grade_point_value = aa_grade_point.text.toString().toDoubleOrNull() ?: 0.0
            var cc_grade_point_value = cc_grade_point.text.toString().toDoubleOrNull() ?: 0.0
            var iti_grade_point_value = iti_grade_point.text.toString().toDoubleOrNull() ?: 0.0
            var ns_grade_point_value = ns_grade_point.text.toString().toDoubleOrNull() ?: 0.0
            var mad_grade_point_value = mad_grade_point.text.toString().toDoubleOrNull() ?: 0.0

            var sum =
                web_grade_point_value + aa_grade_point_value + cc_grade_point_value + iti_grade_point_value + ns_grade_point_value + mad_grade_point_value
            var gpa_value = sum / 6
            gpa.text = String.format("%.2f", gpa_value)


            when (gpa_value) {

                in 4.00..4.00 -> final_grade.text = "A+"
                in 3.50..3.95 -> final_grade.text = "A"
                in 3.00..3.45 -> final_grade.text = "B"
                in 2.50..2.95 -> final_grade.text = "C"
                in 2.00..2.45 -> final_grade.text = "D"
                in 0.00..2.00 -> final_grade.text = "F"

            }

            var percentage = (gpa_value*100) / 4.00;
            final_percentage.text = String.format("%.2f%%", percentage)

            if (percentage >= 50) {
                final_status.text = "Promoted"
            } else {
                final_status.text = "Fail"

            }

            Toast.makeText(this, "Calculation Performed Successfully", Toast.LENGTH_SHORT).show()


        }



    }





    private fun setupTextWatcher(scoreEditText: EditText, gradeTextView: TextView, gradePointTextView: TextView) {
        scoreEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val score = s?.toString()?.toIntOrNull() ?: 0
                val (grade, gradePoint) = calculateGradeAndGradePoint(score)
                gradeTextView.text = grade
                gradePointTextView.text = String.format("%.2f", gradePoint)
            }
        })
    }

    private fun calculateGradeAndGradePoint(score: Int): Pair<String, Double> {
        return when (score) {

            100->Pair("A+", 4.00)
            99 -> Pair("A+", 4.00)
            98 -> Pair("A+", 4.00)
            97 -> Pair("A+", 4.00)
            96 -> Pair("A+", 4.00)
            95 -> Pair("A+", 4.00)
            94 -> Pair("A+", 4.00)
            93 -> Pair("A+", 4.00)
            92 -> Pair("A+", 4.00)
            91 -> Pair("A+", 4.00)
            90 -> Pair("A+", 4.00)
            89 -> Pair("A", 3.95)
            88 -> Pair("A", 3.90)
            87 -> Pair("A", 3.85)
            86 -> Pair("A", 3.80)
            85 -> Pair("A", 3.75)
            84 -> Pair("A", 3.70)
            83 -> Pair("A", 3.65)
            82 -> Pair("A", 3.60)
            81 -> Pair("A", 3.55)
            80 -> Pair("A", 3.50)
            79 -> Pair("B", 3.45)
            78 -> Pair("B", 3.40)
            77 -> Pair("B", 3.35)
            76 -> Pair("B", 3.30)
            75 -> Pair("B", 3.25)
            74 -> Pair("B", 3.20)
            73 -> Pair("B", 3.15)
            72 -> Pair("B", 3.10)
            71 -> Pair("B", 3.05)
            70 -> Pair("B", 3.00)
            69 -> Pair("C", 2.95)
            68 -> Pair("C", 2.90)
            67 -> Pair("C", 2.85)
            66 -> Pair("C", 2.80)
            65 -> Pair("C", 2.75)
            64 -> Pair("C", 2.70)
            63 -> Pair("C", 2.65)
            62 -> Pair("C", 2.60)
            61 -> Pair("C", 2.55)
            60 -> Pair("C", 2.50)
            59 -> Pair("D", 2.45)
            58 -> Pair("D", 2.40)
            57 -> Pair("D", 2.35)
            56 -> Pair("D", 2.30)
            55 -> Pair("D", 2.25)
            54 -> Pair("D", 2.20)
            53 -> Pair("D", 2.15)
            52 -> Pair("D", 2.10)
            51 -> Pair("D", 2.05)
            50 -> Pair("D", 2.00)

            else -> Pair("F", 0.0)

        }


    }

}

