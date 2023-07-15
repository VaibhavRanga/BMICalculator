package com.example.bmicalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etWeight : EditText = findViewById(R.id.etWeight)
        val etHeight : EditText = findViewById(R.id.etHeight)
        val btnCalculate : Button = findViewById(R.id.btnCalculate)
        
        btnCalculate.setOnClickListener {
            val weight = etWeight.text.toString()
            val height = etHeight.text.toString()
            val cvResult = findViewById<CardView>(R.id.cvResult)
            cvResult.visibility = INVISIBLE

            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }

            if (isAnyFieldEmpty(weight, height)) {
                cvResult.visibility = VISIBLE
                val bmi = weight.toFloat() / ((height.toFloat() * height.toFloat()) / 10000)
                val bmiTwoDigits = String.format("%.1f", bmi).toFloat()
                displayBMI(bmiTwoDigits)
            }
        }
    }

    private fun isAnyFieldEmpty(weight : String?, height: String?) :Boolean {
        return when {
            weight.isNullOrEmpty() -> {Toast.makeText(
                this,
                "Please enter weight",
                Toast.LENGTH_SHORT
            ).show()
                false
            }
            height.isNullOrEmpty() -> {Toast.makeText(
                this,
                "Please enter height",
                Toast.LENGTH_SHORT
            ).show()
                false
            }
            else -> true
        }
    }

    private fun displayBMI(bmi : Float) {
        val tvBMI : TextView = findViewById(R.id.tvBMI)
        val tvHealthStatus : TextView = findViewById(R.id.tvHealthStatus)
        tvBMI.text = "$bmi"
        val displayHealthStatus: String
        val color: Int
        if (bmi < 18.5) {
            displayHealthStatus = "Underweight"
            color = R.color.under_weight
        }
        else if (bmi in 18.5..24.9){
            displayHealthStatus = "Healthy"
            color = R.color.healthy
        }
        else if (bmi in 25.0..29.9) {
            displayHealthStatus = "Overweight"
            color = R.color.over_weight
        }
        else {
            displayHealthStatus = "Obese"
            color = R.color.obese
        }
        tvHealthStatus.setTextColor(ContextCompat.getColor(this, color))
        tvHealthStatus.text = displayHealthStatus
    }
}