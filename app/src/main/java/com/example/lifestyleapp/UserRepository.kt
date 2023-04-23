package com.example.lifestyleapp

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.json.JSONException
import java.io.IOException
import java.net.URL

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: UserData): Long {
        return userDao.insertUser(user)
    }

    suspend fun getUser(userId: Int): UserData? {
        return userDao.getUser(userId)
    }
}
