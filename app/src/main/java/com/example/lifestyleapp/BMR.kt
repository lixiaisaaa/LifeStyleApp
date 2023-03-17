package com.example.lifestyleapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BMR : AppCompatActivity() {


    private var bmrString: String? = null
    private var tv_BMR: TextView? = null
    private var backButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        val receivedIntent = intent
        bmrString = receivedIntent.getStringExtra("BMR")
        backButton = findViewById(R.id.button_back1)
        tv_BMR = findViewById(R.id.bmr)
        tv_BMR!!.text = bmrString

        backButton!!.setOnClickListener {
            onBackPressed()
        }

    }



}