package com.example.lifestyleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.content.ContentValues
import androidx.core.net.toFile
import java.io.File
import java.net.URI


class MainActivity : AppCompatActivity() {
    //updated
    lateinit var age_Input: EditText
    lateinit var name_Input: EditText
    lateinit var sexual_Input: EditText
    lateinit var height_Input: EditText
    lateinit var weight_Input: EditText
    lateinit var city_Input: EditText
    lateinit var country_Input: EditText
    //lateinit var save_bnt: Button
    lateinit var activity_Level: EditText
    private lateinit var takePicLauncher: ActivityResultLauncher<String>
    private lateinit var imageView: ImageView
    private val db by lazy {
        MyDBHelper(this, "info.db").writableDatabase
    }

//    var photo : Uri? = null
//    var bitMap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        age_Input = findViewById(R.id.age_Box)
        name_Input = findViewById(R.id.name_Box)
        sexual_Input = findViewById(R.id.sexual_Box)
        weight_Input = findViewById(R.id.weight_Box)
        height_Input = findViewById(R.id.height_Box)
        city_Input = findViewById(R.id.city_Box)
        country_Input = findViewById(R.id.country_Box)
        activity_Level = findViewById(R.id.activity_Box)
        imageView = findViewById(R.id.image)


        takePicLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            Log.d("MainActivity", "onCreate: uri=$it, ")
            imageView.setImageURI(it)
        }

        imageView.setOnClickListener {
            takePicLauncher.launch("image/*")
        }

        // db operation
        with(db) {
            insert(
                MyDBHelper.DB_TABLE,
                null,
                ContentValues().apply {
                    put("name", name_Input.text.toString())
                    put("age", age_Input.text.toString())
                    put("sex", sexual_Input.text.toString())
                    put("location", weight_Input.text.toString())
                    put("height", height_Input.text.toString())
                    put("weight", weight_Input.text.toString())
                    put("activitylevel", activity_Level.text.toString())
                }
            )
            close()
        }

    }


}