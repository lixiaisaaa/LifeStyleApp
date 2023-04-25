package com.example.lifestyleapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class WeatherFragment : Fragment(), View.OnClickListener {
    private var mEtLocation: EditText? = null
    private var mTvTemp: TextView? = null
    private var mTvPress: TextView? = null
    private var mTvHum: TextView? = null
    private val mWeatherData: WeatherData? = null
    private var mBtSubmit: Button? = null
    private var mWeatherViewModel: WeatherViewModel? = null
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val userId = 1 // Replace with the actual user ID
        userViewModel.setUserId(userId)
        userViewModel.getUser(userId).observe(viewLifecycleOwner, Observer { user ->
            if(user != null) {
                if(user.city != null){
                    mEtLocation!!.setText(user.city)
                }


            }

        })

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        //Get the edit text and all the text views
        mEtLocation = view.findViewById<View>(R.id.et_location) as EditText
        mTvTemp = view.findViewById<View>(R.id.tv_temp) as TextView
        mTvPress = view.findViewById<View>(R.id.tv_pressure) as TextView
        mTvHum = view.findViewById<View>(R.id.tv_humidity) as TextView
        if (savedInstanceState != null) {
            val Lo = savedInstanceState.getString("loc")
            val temp = savedInstanceState.getString("tvTemp")
            val hum = savedInstanceState.getString("tvHum")
            val press = savedInstanceState.getString("tvPress")
            if (Lo != null) mEtLocation!!.setText(Lo)
            if (temp != null) mTvTemp!!.text = "" + temp
            if (hum != null) mTvHum!!.text = "" + hum
            if (press != null) mTvPress!!.text = "" + press
        }

        mBtSubmit = view.findViewById<View>(R.id.button_submit) as Button
        mBtSubmit!!.setOnClickListener(this)


        //Create the view model
        mWeatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        //Set the observer
        mWeatherViewModel!!.data.observe(viewLifecycleOwner, nameObserver)
        return view
    }

    //create an observer that watches the LiveData<WeatherData> object
    val nameObserver: Observer<WeatherData?> =
        Observer<WeatherData?> { weatherData -> // Update the UI if this data variable changes
            if (weatherData != null) {
                mTvTemp!!.text = "" + Math.round(weatherData.temperature.temp - 273.15) + " C"
                mTvHum!!.text = "" + weatherData.currentCondition.humidity + "%"
                mTvPress!!.text = "" + weatherData.currentCondition.pressure + " hPa"
            }
        }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_submit -> {

                //Get the string from the edit text and sanitize the input
                val inputFromEt = mEtLocation!!.text.toString().replace(' ', '&')
                loadWeatherData(inputFromEt)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("loc", mEtLocation!!.text.toString())
        outState.putString("tvTemp", mTvTemp!!.text.toString())
        outState.putString("tvHum", mTvHum!!.text.toString())
        outState.putString("tvPress", mTvPress!!.text.toString())
    }

    private fun loadWeatherData(location: String) {
        mWeatherViewModel!!.setLocation(location)
    }
}