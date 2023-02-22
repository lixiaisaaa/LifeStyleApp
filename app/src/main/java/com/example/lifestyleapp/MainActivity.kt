package com.example.lifestyleapp

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap

const val age_text = "AG_TEXT"
const val name_text = "NM_TEXT"
const val height_text = "HT_TEXT"
const val weight_text = "WT_TEXT"
const val sex_text = "SX_TEXT"
const val country_text = "CRT_TEXT"
const val city_text = "CT_TEXT"
const val activity_text = "ACT_TEXT"
class MainActivity : AppCompatActivity() ,View.OnClickListener{
    //updated
    private var age_Input: EditText? = null
    private var country_Input: EditText? = null
    private var city_Input: EditText? = null
    private var name_Input: EditText? = null
    private var height_Input: EditText? = null
    private var weight_Input: EditText? = null
    private var sex_Input: EditText? = null
    private var activity_Input: EditText? = null
    private var mButtonCamera: Button? = null
    private var mButtonBMR: Button? = null
    private var mButtonPro: Button? = null

    //pics
    private lateinit var takePicLauncher: ActivityResultLauncher<String>
    private lateinit var imageView: ImageView

    //Data store
    private var mStringName: String? = null
    private var mStringAge: String? = null
    private var mHeight: String? = null
    private var mWeight: String? = null
    private var mSexual: String? = null
    private var mCountry: String? = null
    private var mCity: String? = null
    private var mlvl: String? = null

    private var suggestions_Act = arrayOf("High","Medium","Low")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,suggestions_Act)
        val autocompletetextview = findViewById<AutoCompleteTextView>(R.id.autoComplete_act)
        autocompletetextview.threshold=0
        autocompletetextview.setAdapter(adapter)


        age_Input = findViewById(R.id.et_Age)
        name_Input = findViewById(R.id.et_Name)
        height_Input = findViewById(R.id.et_Height)
        weight_Input = findViewById(R.id.et_Weight)
        sex_Input = findViewById(R.id.et_sex)
        activity_Input = findViewById(R.id.autoComplete_act)
        country_Input = findViewById(R.id.et_Country)
        city_Input = findViewById(R.id.et_City)
        imageView = findViewById(R.id.image)

        mButtonCamera = findViewById(R.id.button_pic)
        mButtonBMR = findViewById(R.id.button_BMR)
        mButtonPro = findViewById(R.id.button_Profile)

        takePicLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            Log.d("MainActivity", "onCreate: uri=$it, ")
            imageView.setImageURI(it)
        }

        mButtonCamera!!.setOnClickListener(this)
        mButtonBMR!!.setOnClickListener(this)
        mButtonPro!!.setOnClickListener(this)

        imageView.setOnClickListener {
            takePicLauncher.launch("image/*")
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mStringName = name_Input!!.text.toString()
        mStringAge = age_Input!!.text.toString()
        mHeight = height_Input!!.text.toString()
        mWeight = weight_Input!!.text.toString()
        mSexual = sex_Input!!.text.toString()
        mCountry = country_Input!!.text.toString()
        mCity = city_Input!!.text.toString()


        outState.putString(name_text,mStringName)
        outState.putString(age_text,mStringAge)
        outState.putString(height_text,mHeight)
        outState.putString(weight_text,mWeight)
        outState.putString(sex_text,mSexual)
        outState.putString(country_text,mCountry)
        outState.putString(city_text,mCity)




        if(imageView!!.drawable != null){
            outState.putParcelable("BITMAP",imageView!!.drawable.toBitmap())
        }
        //outState.putParcelable("BITMAP",mBitmap)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        name_Input!!.setText(savedInstanceState.getString(name_text))
        age_Input!!.setText(savedInstanceState.getString(name_text))
        height_Input!!.setText(savedInstanceState.getString(height_text))
        weight_Input!!.setText(savedInstanceState.getString(weight_text))
        sex_Input!!.setText(savedInstanceState.getString(sex_text))
        city_Input!!.setText(savedInstanceState.getString(city_text))
        country_Input!!.setText(savedInstanceState.getString(country_text))


        if(Build.VERSION.SDK_INT >= 33){
            imageView!!.setImageBitmap(savedInstanceState.getParcelable("BITMAP", Bitmap::class.java))
        }else{
            imageView!!.setImageBitmap(savedInstanceState.getParcelable("BITMAP"))
        }

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button_pic ->{
                //The button press should open a camera
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try{
                    cameraActivity.launch(cameraIntent)
                }catch(ex: ActivityNotFoundException){
                    //Do error handling here
                }
            }
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

                if(mHeight.isNullOrBlank() || mWeight.isNullOrBlank() || mStringAge.isNullOrBlank() ||
                    mSexual.isNullOrBlank() || mCity.isNullOrBlank() || mStringName.isNullOrBlank() ||
                    mCountry.isNullOrBlank() || mlvl.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, "enter all information", Toast.LENGTH_SHORT).show()
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
                var weightV = mWeight!!.toDouble()
                var heightV = mHeight!!.toDouble()
                var ageV = mStringAge!!.toDouble()
                var bmr = ""
                if(mSexual == "M"){
                    bmr = String.format("%.3f", 88.362 + (13.397 * weightV) + (4.799* heightV) - (5.677*ageV))
                }else if(mSexual == "W"){
                    bmr = String.format("%.3f", 447.593 + (9.247 * weightV) + (3.098* heightV) - (4.330*ageV))
                }

                if(mHeight.isNullOrBlank() || mWeight.isNullOrBlank() || mStringAge.isNullOrBlank() || mSexual.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, "enter all height, weight, age and sex", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity, "Welcome!\"", Toast.LENGTH_SHORT).show()
                    val messageIntent = Intent(this, BMR::class.java)
                    messageIntent.putExtra("BMR", bmr)
                    this.startActivity(messageIntent)
                }
            }
        }
    }
    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK) {

            imageView = findViewById(R.id.image)

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                imageView!!.setImageBitmap(thumbnailImage)
            }
            else{
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                imageView!!.setImageBitmap(thumbnailImage)
            }


        }
    }


}