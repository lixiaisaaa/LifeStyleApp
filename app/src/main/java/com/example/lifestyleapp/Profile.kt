package com.example.lifestyleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class Profile : AppCompatActivity() {
    private var mStringName: String? = null
    private var mStringAge: String? = null
    private var mHeight: String? = null
    private var mWeight: String? = null
    private var mSexual: String? = null
    private var mCountry: String? = null
    private var mCity: String? = null
    private var mlvl: String? = null

    private var age_Input: TextView? = null
    private var country_Input: TextView? = null
    private var city_Input: TextView? = null
    private var name_Input: TextView? = null
    private var height_Input: TextView? = null
    private var weight_Input: TextView? = null
    private var sex_Input: TextView? = null
    private var activity_Input: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val receivedIntent = intent
        mStringName = receivedIntent.getStringExtra(name_text)
        mStringAge = receivedIntent.getStringExtra(age_text)
        mHeight =  receivedIntent.getStringExtra(height_text)
        mWeight = receivedIntent.getStringExtra(weight_text)
        mSexual = receivedIntent.getStringExtra(sex_text)
        mCountry = receivedIntent.getStringExtra(country_text)
        mCity = receivedIntent.getStringExtra(city_text)
        mlvl = receivedIntent.getStringExtra(activity_text)

        age_Input = findViewById(R.id.age)
        country_Input = findViewById(R.id.country)
        city_Input = findViewById(R.id.city)
        name_Input = findViewById(R.id.name)
        height_Input = findViewById(R.id.height)
        weight_Input = findViewById(R.id.weight)
        sex_Input = findViewById(R.id.sex)
        activity_Input = findViewById(R.id.actvity)

        age_Input!!.text = mStringAge
        country_Input!!.text = mCountry
        city_Input!!.text = mCity
        name_Input!!.text = mStringName
        height_Input!!.text = mHeight
        weight_Input!!.text = mWeight
        sex_Input!!.text = mSexual
        activity_Input!!.text = mlvl
    }


}