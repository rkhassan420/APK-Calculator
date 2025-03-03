package com.example.calculator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val expression: String,
    val result: String = ""

)
