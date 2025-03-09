package com.example.calculator

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function
import java.lang.ArithmeticException
import java.lang.Exception

class ScientificCalculator : ComponentActivity() {
    private lateinit var historyDatabase: HistoryDatabase
    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scientific_calculator)

        historyDatabase = HistoryDatabase.getDatabase(this)

        val input = findViewById<TextView>(R.id.txtInput)
        val display = findViewById<TextView>(R.id.txtSolution)

        val zero = findViewById<Button>(R.id.btnZero)
        val dot = findViewById<Button>(R.id.btnDecimal)
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

        var btnPower = findViewById<Button>(R.id.btnPower)
        var btnLog = findViewById<Button>(R.id.btnLog)
        var btnSin = findViewById<Button>(R.id.btnSin)
        var btnCos = findViewById<Button>(R.id.btnCos)
        var btnTan  = findViewById<Button>(R.id.btnTan)
        var btnFact = findViewById<Button>(R.id.btnFact)
        var btnSqrt = findViewById<Button>(R.id.btnSqrt)
        var btnPi = findViewById<Button>(R.id.btnPi)
        var btnOpenBrac = findViewById<Button>(R.id.btnOpenBrac)
        var btnCloseBrac = findViewById<Button>(R.id.btnCloseBrac)

        val clear = findViewById<Button>(R.id.btnClear)
        val back = findViewById<ImageView>(R.id.btnBack)
        val calc = findViewById<Button>(R.id.btnEquals)

        val moreIcon = findViewById<ImageView>(R.id.more)
        val percent = findViewById<TextView>(R.id.percent)
        val backBtn = findViewById<ImageView>(R.id.scientificBtn)

        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }

        percent.setOnClickListener {
            startActivity(Intent(this, Listicons::class.java))
            overridePendingTransition(0, 0)
            finish()
        }

        moreIcon.setOnClickListener {
            showDialog()
        }

        zero.setOnClickListener {   input.text = "${input.text}0"  }
        dot.setOnClickListener  {   input.text = "${input.text}."  }
        one.setOnClickListener  {   input.text = "${input.text}1"  }
        two.setOnClickListener  {   input.text = "${input.text}2"  }
        three.setOnClickListener{   input.text = "${input.text}3"  }
        four.setOnClickListener {   input.text = "${input.text}4"  }
        five.setOnClickListener {   input.text = "${input.text}5"  }
        six.setOnClickListener  {   input.text = "${input.text}6"  }
        seven.setOnClickListener{   input.text = "${input.text}7"  }
        eight.setOnClickListener{   input.text = "${input.text}8"  }
        nine.setOnClickListener {   input.text = "${input.text}9"  }

        plus.setOnClickListener     {   input.text = "${input.text}+"   }
        subtract.setOnClickListener {   input.text = "${input.text}-"   }
        multiply.setOnClickListener {   input.text = "${input.text}×"   }
        divide.setOnClickListener   {   input.text = "${input.text}/"   }

        btnPower.setOnClickListener {   input.text = "${input.text}^"   }
        btnLog.setOnClickListener   {   input.text = "${input.text}log("   }
        btnSin.setOnClickListener   {   input.text = "${input.text}sin("   }
        btnCos.setOnClickListener   {   input.text = "${input.text}cos("   }
        btnTan.setOnClickListener   {   input.text = "${input.text}tan("   }
        btnFact.setOnClickListener  {   input.text = "${input.text}!"   }
        btnSqrt.setOnClickListener  {   input.text = "${input.text}sqrt("   }
        btnPi.setOnClickListener    {   input.text = "${input.text}pi"  }
        btnOpenBrac.setOnClickListener{ input.text = "${input.text}("   }
        btnCloseBrac.setOnClickListener{input.text = "${input.text})"   }


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





        calc.setOnClickListener {
            val expressionText = input.text.toString()
                .replace(Regex("(\\d+)!"), "fact($1)")
                .replace("×", "*")

            try {
                // Custom factorial function
                val factorialFunc = object : Function("fact", 1) {
                    override fun apply(vararg args: Double): Double {
                        val n = args[0].toInt()
                        if (n < 0) throw ArithmeticException("Negative factorial not allowed")
                        return factorial(n).toDouble()
                    }
                }

                val expression = ExpressionBuilder(expressionText)
                    .function(factorialFunc)  // Register the custom factorial function
                    .variables("pi", "e")    // Supports pi and e
                    .build()

                // Set variables (pi and e)
                expression.setVariable("pi", Math.PI)
                expression.setVariable("e", Math.E)

                // Evaluate the expression
                val result = expression.evaluate()
                val longResult = result.toLong()

                // Format the result
                val finalResult = if (result == longResult.toDouble()) {
                    longResult.toString()
                } else {
                    String.format("%.6f", result)
                }

                display.text = "= $finalResult"

                // Save history to Room Database
                saveHistory(expressionText, finalResult)

            } catch (e: ArithmeticException) {
                display.text = "Math Error"
            } catch (e: Exception) {
                display.text = "Error"
            }
        }

    }

    fun factorial(n: Int): Long {
        var result = 1L
        for (i in 1..n) {
            result *= i
        }
        return result
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
            overridePendingTransition(0, 0)

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


