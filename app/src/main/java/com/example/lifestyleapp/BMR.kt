package com.example.lifestyleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class BMR : AppCompatActivity() {


    private var bmrString: String? = null
    private var tv_BMR: TextView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        val receivedIntent = intent
        bmrString = receivedIntent.getStringExtra("BMR")

        tv_BMR = findViewById(R.id.bmr)
        tv_BMR!!.text = bmrString

    }



}