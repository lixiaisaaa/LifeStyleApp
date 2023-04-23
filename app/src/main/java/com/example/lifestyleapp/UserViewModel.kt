package com.example.lifestyleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository
    private val userId: MutableLiveData<Int> = MutableLiveData()

    init {
        val userDao = UserRoomDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun insertUser(user: UserData): LiveData<Long> {
        val insertResult = MutableLiveData<Long>()
        viewModelScope.launch {
            insertResult.value = userRepository.insertUser(user)
        }
        return insertResult
    }

    fun getUser(userId: Int): LiveData<UserData> {
        val userResult = MutableLiveData<UserData>()
        viewModelScope.launch {
            userResult.value = userRepository.getUser(userId)
        }
        return userResult
    }

    fun setUserId(userId: Int) {
        this.userId.value = userId
    }

    fun getUserId(): LiveData<Int> = userId
}