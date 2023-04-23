package com.example.lifestyleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WeatherViewModel(application: Application?) : AndroidViewModel(
    application!!
) {
    private val jsonData: MutableLiveData<WeatherData>
    private val mWeatherRepository: WeatherRepository

    init {
        mWeatherRepository = WeatherRepository.getInstance(application!!)
        jsonData = mWeatherRepository.getData()
    }

    fun setLocation(location: String?) {
        mWeatherRepository.setLocation(location!!)
    }

    val data: LiveData<WeatherData>
        get() = jsonData
}