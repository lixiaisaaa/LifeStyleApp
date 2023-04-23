package com.example.lifestyleapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val age: String,
    val height: String,
    val weight: String,
    val sex: String,
    val country: String,
    val city: String,
    val activityLevel: String
)