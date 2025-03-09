package com.example.calculator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class History : ComponentActivity() {  // ✅ Changed from ComponentActivity to AppCompatActivity

    private lateinit var listViewHistory: ListView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyDatabase: HistoryDatabase

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // ✅ Corrected variable initialization
        listViewHistory = findViewById(R.id.listViewHistory)
        historyDatabase = HistoryDatabase.getDatabase(this)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }

        loadHistory()
        val btnDel = findViewById<ImageView>(R.id.btnDel)

        btnDel.setOnClickListener {
            delAllHistory()
        }
    }

    private fun loadHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val historyList = historyDatabase.historyDao().getAllHistory()
            withContext(Dispatchers.Main) {
                historyAdapter = HistoryAdapter(this@History, historyList)
                listViewHistory.adapter = historyAdapter
            }
        }
    }



    private fun delAllHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            historyDatabase.historyDao().deleteAllHistory()
            withContext(Dispatchers.Main) {
                historyAdapter.clear()
                historyAdapter.notifyDataSetChanged()
            }
        }
    }

}

