package com.example.calculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HistoryAdapter(context: Context, private val historyList: List<HistoryEntity>) :
    ArrayAdapter<HistoryEntity>(context, R.layout.history_item, historyList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.history_item, parent, false)

        val expressionText = view.findViewById<TextView>(R.id.txtExpression)
        val resultText = view.findViewById<TextView>(R.id.txtResult)

        val history = historyList[position]
        expressionText.text = history.expression
        resultText.text = history.result

        return view
    }
}
