package com.example.lifestyleapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class WeatherActivity : AppCompatActivity() , View.OnClickListener{
    //updated



    //private var suggestions_Act = arrayOf("High","Medium","Low")
    private var weatherFragment: Fragment? = null
    private  var mStringCity: String? = null
    private  var backButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme_Wallpaper_NoTitleBar);
        val receivedIntent = intent

        mStringCity = receivedIntent.getStringExtra(city_text)

        Log.d("city", "city" + mStringCity.toString())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)


        if (savedInstanceState != null) {
            weatherFragment = supportFragmentManager.findFragmentByTag("weather_frag")
        } else {
            val bundle = Bundle()
            bundle.putString("city", mStringCity)
            val weatherFragment = WeatherFragment()
            weatherFragment.arguments = bundle
            val fTrans = supportFragmentManager.beginTransaction()
            fTrans.replace(R.id.fl_frag_weather, weatherFragment, "weather_frag")
            fTrans.commit()
        }

        val backButton: Button = findViewById(R.id.button_weatherBack)
        backButton.setOnClickListener {
            onBackPressed()
        }


    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)


      //  mCountry = country_Input!!.text.toString()
      //  mCity = city_Input!!.text.toString()








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