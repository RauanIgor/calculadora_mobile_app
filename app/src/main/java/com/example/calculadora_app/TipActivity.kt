package com.example.calculadora_app

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.*

class TipActivity : AppCompatActivity() {

    private lateinit var valueSubtotal: TextView
    private lateinit var valueTip: TextView
    private lateinit var valueTotal: TextView
    private lateinit var valuePeople: TextView
    private lateinit var textTipPercent: TextView
    private lateinit var textQtdPeople: TextView

    private var subtotal: String = ""
    private var tipPercent: Int = 10
    private var qtdPeople: Int = 2

    companion object {
        private const val KEY_SUBTOTAL = "key_subtotal"
        private const val KEY_TIP_PERCENT = "key_tip_percent"
        private const val KEY_QTD_PEOPLE = "key_qtd_people"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip)

        valueSubtotal = findViewById(R.id.value_subtotal)
        valueTip = findViewById(R.id.value_tip)
        valueTotal = findViewById(R.id.value_total)
        valuePeople = findViewById(R.id.value_people)
        textTipPercent = findViewById(R.id.text_tip_percent)
        textQtdPeople = findViewById(R.id.text_qtd_people)

        if (savedInstanceState != null) {
            subtotal = savedInstanceState.getString(KEY_SUBTOTAL, "")
            tipPercent = savedInstanceState.getInt(KEY_TIP_PERCENT, 10)
            qtdPeople = savedInstanceState.getInt(KEY_QTD_PEOPLE, 2)
        }

        updateSubtotal()
        updateTipPercent()
        updatePeople()
        updateCalculations()


        findViewById<MaterialButton>(R.id.btn_increase_tip).setOnClickListener {
            tipPercent += 1
            updateTipPercent()
            updateCalculations()
        }

        findViewById<MaterialButton>(R.id.btn_decrease_tip).setOnClickListener {
            if (tipPercent > 0) tipPercent -= 1
            updateTipPercent()
            updateCalculations()
        }


        findViewById<MaterialButton>(R.id.btn_increase_people).setOnClickListener {
            qtdPeople += 1
            updatePeople()
            updateCalculations()
        }

        findViewById<MaterialButton>(R.id.btn_decrease_people).setOnClickListener {
            if (qtdPeople > 1) qtdPeople -= 1
            updatePeople()
            updateCalculations()
        }


        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        for (id in numberButtons) {
            findViewById<MaterialButton>(id).setOnClickListener {
                val digit = (it as MaterialButton).text.toString()
                subtotal += digit
                updateSubtotal()
                updateCalculations()
            }
        }


        findViewById<MaterialButton>(R.id.btnDot).setOnClickListener {
            if (!subtotal.contains(".")) {
                if (subtotal.isEmpty()) subtotal = "0"
                subtotal += "."
                updateSubtotal()
                updateCalculations()
            }
        }


        findViewById<MaterialButton>(R.id.btnBackspace).setOnClickListener {
            if (subtotal.isNotEmpty()) {
                subtotal = subtotal.dropLast(1)
                updateSubtotal()
                updateCalculations()
            }
        }

        findViewById<MaterialButton>(R.id.btnClear).setOnClickListener {
            subtotal = ""
            tipPercent = 10
            qtdPeople = 2
            updateTipPercent()
            updatePeople()
            updateSubtotal()
            updateCalculations()
        }
    }

    private fun updateSubtotal() {
        valueSubtotal.text = formatCurrency(subtotal)
    }

    private fun updateTipPercent() {
        textTipPercent.text = "$tipPercent%"
    }

    private fun updatePeople() {
        textQtdPeople.text = "$qtdPeople"
    }

    private fun updateCalculations() {
        val sub = subtotal.toDoubleOrNull() ?: 0.0
        val tip = sub * tipPercent / 100.0
        val total = sub + tip
        val perPerson = if (qtdPeople > 0) total / qtdPeople else total

        valueTip.text = formatCurrency(tip)
        valueTotal.text = formatCurrency(total)
        valuePeople.text = formatCurrency(perPerson)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SUBTOTAL, subtotal)
        outState.putInt(KEY_TIP_PERCENT, tipPercent)
        outState.putInt(KEY_QTD_PEOPLE, qtdPeople)
    }

    private fun formatCurrency(value: String): String {
        val number = value.toDoubleOrNull() ?: 0.0
        return formatCurrency(number)
    }

    private fun formatCurrency(value: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return format.format(value)
    }
}
