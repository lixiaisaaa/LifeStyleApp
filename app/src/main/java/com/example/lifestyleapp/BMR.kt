package com.example.lifestyleapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class BMR : AppCompatActivity() {
    // create some global variables
    private var bmrString: String? = null
    private var tv_BMR: TextView? = null
    private var backButton: Button? = null

    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        backButton = findViewById(R.id.button_back1)
        tv_BMR = findViewById(R.id.bmr)
        val userId = 1 // Replace with the actual user ID
        userViewModel.setUserId(userId)
        userViewModel.getUser(userId).observe(this, Observer { user ->
            Log.d("BMR", user.name);
            Log.d("BMR", user.weight);
            Log.d("BMR", user.height);
            Log.d("BMR", user.age);
            Log.d("BMR", user.sex);
            if(user.sex == "Male"){
                bmrString = String.format("%.3f", 88.362 + (13.397 *  user.weight!!.toDouble()) + (4.799* user.height!!.toDouble()) - (5.677*user.age!!.toDouble()))
            }else if(user.sex == "Female"){
                bmrString = String.format("%.3f", 447.593 + (9.247 * user.weight!!.toDouble()) + (3.098*  user.height!!.toDouble()) - (4.330* user.age!!.toDouble()))
            }
            bmrString?.let { Log.d("BMR", it) };

            tv_BMR!!.text = bmrString
        })






        backButton!!.setOnClickListener {
            onBackPressed()
        }

    }



}