package com.example.calculator

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import net.objecthunter.exp4j.ExpressionBuilder
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope


class MainActivity : ComponentActivity() {
    private lateinit var historyDatabase: HistoryDatabase

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainscreen)

        historyDatabase = HistoryDatabase.getDatabase(this)  // Initialize Database


        val input = findViewById<TextView>(R.id.txtInput)
        val display = findViewById<TextView>(R.id.txtSolution)

        val zero = findViewById<Button>(R.id.btnZero)
        val dot = findViewById<Button>(R.id.btnDecimal)
        val double_zero = findViewById<Button>(R.id.doubleZero)
        val one = findViewById<Button>(R.id.btnOne)
        val two = findViewById<Button>(R.id.btnTwo)
        val three = findViewById<Button>(R.id.btnThree)
        val four = findViewById<Button>(R.id.btnFour)
        val five = findViewById<Button>(R.id.btnFive)
        val six = findViewById<Button>(R.id.btnSix)
        val seven = findViewById<Button>(R.id.btnSeven)
        val eight = findViewById<Button>(R.id.btnEight)
        val nine = findViewById<Button>(R.id.btnNine)

        val plus = findViewById<Button>(R.id.btnAdd)
        val subtract = findViewById<Button>(R.id.btnSubtract)
        val multiply = findViewById<Button>(R.id.btnMultiply)
        val divide = findViewById<Button>(R.id.btnDivide)

        val clear = findViewById<Button>(R.id.btnClear)
        val back = findViewById<ImageView>(R.id.btnBack)
        val calc = findViewById<Button>(R.id.btnEquals)

        val moreIcon = findViewById<ImageView>(R.id.more)

        moreIcon.setOnClickListener {
            showDialog()
        }




        zero.setOnClickListener {
            input.text = "${input.text}${zero.text}"
        }
        double_zero.setOnClickListener {
            input.text = "${input.text}${double_zero.text}"
        }
        dot.setOnClickListener {
            input.text = "${input.text}${dot.text}"
        }
        one.setOnClickListener {
            input.text = "${input.text}${one.text}"
        }
        two.setOnClickListener {
            input.text = "${input.text}${two.text}"
        }
        three.setOnClickListener {
            input.text = "${input.text}${three.text}"
        }
        four.setOnClickListener {
            input.text = "${input.text}${four.text}"
        }
        five.setOnClickListener {
            input.text = "${input.text}${five.text}"
        }
        six.setOnClickListener {
            input.text = "${input.text}${six.text}"
        }
        seven.setOnClickListener {
            input.text = "${input.text}${seven.text}"
        }
        eight.setOnClickListener {
            input.text = "${input.text}${eight.text}"
        }
        nine.setOnClickListener {
            input.text = "${input.text}${nine.text}"
        }


        plus.setOnClickListener {
            input.text = "${input.text}${plus.text}"
        }
        subtract.setOnClickListener {
            input.text = "${input.text}${subtract.text}"
        }
        multiply.setOnClickListener {
            input.text = "${input.text}${multiply.text}"
        }
        divide.setOnClickListener {
            input.text = "${input.text}${divide.text}"
        }



        back.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                input.text = text.substring(0, text.length - 1)
            }
        }

        //clean input Field
        clear.setOnClickListener {
            input.text = ""
            display.text = ""
        }
//
//        calc.setOnClickListener {
//
//            val expression = ExpressionBuilder(input.text.toString()).build()
//            val result = expression.evaluate()
//            val longResult = result.toLong()
//
//            if (result == longResult.toDouble()){
//                display.text = longResult.toString()
//            }
//            else{
//                display.text = result.toString()
//            }
//
//
//        }

        calc.setOnClickListener {
            val expressionText = input.text.toString()

            try {
                val expression = ExpressionBuilder(expressionText).build()
                val result = expression.evaluate()
                val longResult = result.toLong()

                val finalResult = if (result == longResult.toDouble()) {
                    longResult.toString()
                } else {
                    result.toString()
                }

                display.text = finalResult

                // Save history to Room Database
                saveHistory(expressionText, finalResult)

            } catch (e: Exception) {
                display.text = "Error"
            }


        }

    }

    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_menu)

        val window = dialog.window
        val layoutParams = WindowManager.LayoutParams()

        if (window != null) {
            layoutParams.copyFrom(window.attributes)

            layoutParams.gravity = Gravity.TOP or Gravity.END  // Align top-right
            layoutParams.x = 20
            layoutParams.y = 80

            window.attributes = layoutParams
        }

        val btnHistory = dialog.findViewById<Button>(R.id.btnHistory)

        btnHistory.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, History::class.java))
        }


        dialog.show()
    }


    private fun saveHistory(expression: String, result: String) {
        val historyEntity = HistoryEntity(expression = expression, result = result)

        lifecycleScope.launch(Dispatchers.IO) {
            historyDatabase.historyDao().insertHistory(historyEntity)
        }
    }

}


