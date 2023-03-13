package com.example.lifestyleapp

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

class WeatherActivity : AppCompatActivity() , View.OnClickListener{
    //updated


    private var mButtonBMR: Button? = null
    private var mButtonPro: Button? = null
    private var mButtonHikes: Button? = null
    private var mButtonCalculate: Button?=null




    //Data store


    private var mCountry: String? = null
    private var mCity: String? = null


    //private var suggestions_Act = arrayOf("High","Medium","Low")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)


        mButtonBMR = findViewById(R.id.button_BMR)
        mButtonPro = findViewById(R.id.button_Profile)
        mButtonHikes = findViewById(R.id.button_hikes)
        mButtonCalculate = findViewById(R.id.button_calculate)




        mButtonBMR!!.setOnClickListener(this)
        mButtonPro!!.setOnClickListener(this)
        mButtonCalculate!!.setOnClickListener(this)


        mButtonHikes!!.setOnClickListener {
            // request permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MapActivity.PERMISSIONS_REQUEST_LOCATION
                )
            } else {
                // User has already granted location permission, open MapActivity
                startActivity(Intent(this, realMap::class.java))
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)


      //  mCountry = country_Input!!.text.toString()
      //  mCity = city_Input!!.text.toString()



        outState.putString(country_text,mCountry)
        outState.putString(city_text,mCity)




        //outState.putParcelable("BITMAP",mBitmap)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)




    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button_weather->{

            }


           /*
            R.id.button_Profile ->{
                name_Input = findViewById(R.id.et_Name)
                height_Input = findViewById(R.id.et_Height)
                weight_Input = findViewById(R.id.et_Weight)
                sex_Input = findViewById(R.id.et_sex)
                age_Input = findViewById(R.id.et_Age)
                country_Input = findViewById(R.id.et_Country)
                city_Input = findViewById(R.id.et_City)
                activity_Input = findViewById(R.id.autoComplete_act)

                mStringName = name_Input!!.text.toString()
                mHeight = height_Input!!.text.toString()
                mWeight = weight_Input!!.text.toString()
                mSexual = sex_Input!!.text.toString()
                mStringAge = age_Input!!.text.toString()
                mCity = city_Input!!.text.toString()
                mCountry = country_Input!!.text.toString()
                mlvl = activity_Input!!.text.toString()

                if(mStringName.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, "at least enter your name", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity, "Welcome!\"", Toast.LENGTH_SHORT).show()
                    val messageIntent = Intent(this, Profile::class.java)
                    messageIntent.putExtra(name_text,mStringName)
                    messageIntent.putExtra(height_text,mHeight)
                    messageIntent.putExtra(weight_text,mWeight)
                    messageIntent.putExtra(sex_text,mSexual)
                    messageIntent.putExtra(age_text,mStringAge)
                    messageIntent.putExtra(city_text,mCity)
                    messageIntent.putExtra(country_text,mCountry)
                    messageIntent.putExtra(activity_text,mlvl)
                    this.startActivity(messageIntent)
                }
            }
            R.id.button_BMR ->{
                height_Input = findViewById(R.id.et_Height)
                weight_Input = findViewById(R.id.et_Weight)
                sex_Input = findViewById(R.id.et_sex)
                age_Input = findViewById(R.id.et_Age)

                mHeight = height_Input!!.text.toString()
                mWeight = weight_Input!!.text.toString()
                mSexual = sex_Input!!.text.toString()
                mStringAge = age_Input!!.text.toString()

                var weightV = 0.0
                var heightV = 0.0
                var ageV = 0.0
                if(mWeight != ""){
                    weightV = mWeight!!.toDouble()
                }
                if(mHeight != ""){
                    heightV = mHeight!!.toDouble()
                }
                if(mStringAge != ""){
                    ageV = mStringAge!!.toDouble()
                }
                var bmr = ""


                if(mHeight.isNullOrBlank() || mWeight.isNullOrBlank() || mStringAge.isNullOrBlank() || mSexual.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, "enter all height, weight, age and sex", Toast.LENGTH_SHORT).show()
                }else{
                    if(mSexual == "Men"){
                        bmr = String.format("%.3f", 88.362 + (13.397 * weightV) + (4.799* heightV) - (5.677*ageV))
                    }else if(mSexual == "Women"){
                        bmr = String.format("%.3f", 447.593 + (9.247 * weightV) + (3.098* heightV) - (4.330*ageV))
                    }
                    Toast.makeText(this@MainActivity, "Welcome!\"", Toast.LENGTH_SHORT).show()
                    val messageIntent = Intent(this, BMR::class.java)
                    messageIntent.putExtra("BMR", bmr)
                    this.startActivity(messageIntent)
                }


            }

            */
        }
    }





}