package com.example.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                val userId: String,
                val title: String,
                val content: String)
