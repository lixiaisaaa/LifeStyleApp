package com.example.lifestyleapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.activity.viewModels


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

    private var backBottom: Button? = null

    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val receivedIntent = intent
//        mStringName = receivedIntent.getStringExtra(name_text)!!.replace("^\\s+".toRegex(), "")
//        mStringAge = receivedIntent.getStringExtra(age_text)!!.replace("^\\s+".toRegex(), "")
//        mHeight = receivedIntent.getStringExtra(height_text)!!.replace("^\\s+".toRegex(), "")
//        mWeight = receivedIntent.getStringExtra(weight_text)!!.replace("^\\s+".toRegex(), "")
//        mSexual = receivedIntent.getStringExtra(sex_text)!!.replace("^\\s+".toRegex(), "")
//        mCountry = receivedIntent.getStringExtra(country_text)!!.replace("^\\s+".toRegex(), "")
//        mCity = receivedIntent.getStringExtra(city_text)!!.replace("^\\s+".toRegex(), "")
//        mlvl = receivedIntent.getStringExtra(activity_text)!!.replace("^\\s+".toRegex(), "")

        val userId = 0 // Replace with the actual user ID
        userViewModel.setUserId(userId)
        userViewModel.getUser(userId).observe(this, Observer { user ->
            age_Input.text = user.age.toString()
            country_Input.text = user.country
            city_Input.text = user.city
            name_Input.text = user.name
            height_Input.text = user.height.toString()
            weight_Input.text = user.weight.toString()
            sex_Input.text = user.sex
            activity_Input.text = user.activityLevel
        })

        backBottom = findViewById(R.id.button_back2)
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

        backBottom!!.setOnClickListener {
            onBackPressed()
        }
    }


}