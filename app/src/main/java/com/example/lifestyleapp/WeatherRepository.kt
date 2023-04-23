package com.example.lifestyleapp

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.json.JSONException
import java.io.IOException
import java.net.URL

class WeatherRepository private constructor(private val application: Application) {
    private val jsonData: MutableLiveData<WeatherData> = MutableLiveData()
    private var mLocation: String? = null

    init {
        mLocation?.let { loadData() }
    }

    companion object {
        private var instance: WeatherRepository? = null

        @Synchronized
        fun getInstance(application: Application): WeatherRepository {
            if (instance == null) {
                instance = WeatherRepository(application)
            }
            return instance!!
        }
    }

    fun setLocation(location: String) {
        mLocation = location
        loadData()
    }

    fun getData(): MutableLiveData<WeatherData> {
        return jsonData
    }

    private fun loadData() {
        mLocation?.let { FetchWeatherTask().execute(it) }
    }

    private inner class FetchWeatherTask {
        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        fun execute(location: String) {
            coroutineScope.launch {
                val weatherDataURL = NetworkUtils.buildURLFromString(location)
                val jsonWeatherData = try {
                    NetworkUtils.getDataFromURL(weatherDataURL)
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }

                jsonWeatherData?.let { postToMainThread(it) }
            }
        }

        private fun postToMainThread(retrievedJsonData: String) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    jsonData.value = JSONWeatherUtils.getWeatherData(retrievedJsonData)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
