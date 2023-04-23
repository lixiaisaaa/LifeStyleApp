package com.example.lifestyleapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserData): Long

    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUser(userId: Int): UserData?
}