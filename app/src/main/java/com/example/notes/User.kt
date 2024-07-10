package com.example.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val userId: String,
    val displayName: String? = null,
    val email: String? = null
)