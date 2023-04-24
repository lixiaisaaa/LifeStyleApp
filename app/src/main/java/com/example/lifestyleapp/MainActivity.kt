package com.example.lifestyleapp

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.io.ByteArrayOutputStream

const val age_text = "AG_TEXT"
const val name_text = "NM_TEXT"
const val height_text = "HT_TEXT"
const val weight_text = "WT_TEXT"
const val sex_text = "SX_TEXT"
const val country_text = "CRT_TEXT"
const val city_text = "CT_TEXT"
const val activity_text = "ACT_TEXT"
const val activity_textView = "ACT_TEXTVIEW"
class MainActivity : AppCompatActivity() ,View.OnClickListener{
    //updated
    private var age_Input: EditText? = null
    private var country_Input: EditText? = null
    private var city_Input: EditText? = null
    private var name_Input: EditText? = null
    private var height_Input: EditText? = null
    private var weight_Input: EditText? = null
    private var sex_Input: Spinner? = null
    private var activity_Input: Spinner? = null
    private var mButtonCamera: Button? = null
    private var mButtonBMR: Button? = null
    private var mButtonPro: Button? = null
    private var mButtonWeather: Button? = null
    private var mButtonHikes: Button? = null
    private var mButtonCalculate: Button?=null
    private var tv_intake: TextView?= null

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
    private var mIntake:String? = null

    private val sexual = arrayOf("Male","Female")
    private val actLvlSpinner = arrayOf("Sedentary (little or no exercise)","Lightly active (light exercise/work 1-3 days per week)"
        ,"Moderately active (moderate exercise/work 3-5 days per week)","Very active (hard exercise/work 6-7 days a week)",
    "Extra active (very hard exercise/work 6-7 days a week)")

    // database
    private lateinit var userViewModel: UserViewModel

//    val imageView = findViewById<ImageView>(R.id.image)
    var bitmap : Bitmap? = null
    var imageByteArray : ByteArray?= null
    var immutableBitmap : Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sex_Input = findViewById(R.id.et_sex)
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sexual)
        sex_Input!!.adapter = arrayAdapter

        activity_Input = findViewById(R.id.autoComplete_act)
        val arrayAdapter1 = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,actLvlSpinner)
        activity_Input!!.adapter = arrayAdapter1

        age_Input = findViewById(R.id.et_Age)
        name_Input = findViewById(R.id.et_Name)
        height_Input = findViewById(R.id.et_Height)
        weight_Input = findViewById(R.id.et_Weight)

        activity_Input = findViewById(R.id.autoComplete_act)
        country_Input = findViewById(R.id.et_Country)

        city_Input = findViewById(R.id.et_City)
        imageView = findViewById(R.id.image)
        tv_intake = findViewById(R.id.intake)
        mButtonWeather = findViewById(R.id.button_weather)
        mButtonCamera = findViewById(R.id.button_pic)
        mButtonBMR = findViewById(R.id.button_BMR)
        mButtonPro = findViewById(R.id.button_Profile)
        mButtonHikes = findViewById(R.id.button_hikes)
        mButtonCalculate = findViewById(R.id.button_calculate)



        takePicLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            Log.d("MainActivity", "onCreate: uri=$it, ")
            imageView.setImageURI(it)
        }

        mButtonCamera!!.setOnClickListener(this)
        mButtonBMR!!.setOnClickListener(this)
        mButtonPro!!.setOnClickListener(this)
        mButtonCalculate!!.setOnClickListener(this)

        imageView.setOnClickListener {
            takePicLauncher.launch("image/*")
        }

        mButtonHikes!!.setOnClickListener {
            // request permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    100)
            } else {
                // User has already granted location permission, open MapActivity
                startActivity(Intent(this, realMap::class.java))
            }
        }

        mButtonWeather!!.setOnClickListener {
            if(city_Input!!.text.toString().isNullOrBlank()){
                Toast.makeText(this@MainActivity, "Please Enter City before checking the weather", Toast.LENGTH_SHORT).show()
            }else {
                val messageIntent = Intent(this, WeatherActivity::class.java)
                messageIntent.putExtra(city_text,city_Input!!.text.toString())
                this.startActivity(messageIntent)
            }
        }




        // Initialize UserViewModel
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userId = 1 // Replace with the actual user ID
        userViewModel.setUserId(userId)
        userViewModel.getUser(userId).observe(this, Observer { user ->
            if (user != null) {
                age_Input?.setText(user.age.toString())
                country_Input?.setText(user.country)
                city_Input?.setText(user.city)
                name_Input?.setText(user.name)
                height_Input?.setText(user.height.toString())
                weight_Input?.setText(user.weight.toString())

                imageView?.setImageBitmap(user.image?.let { getBitmapFromBytes(it) })

                if (user.sex == "Male") {
                    sex_Input!!.setSelection(0)
                } else {
                    sex_Input!!.setSelection(1)
                }



                if (user.activityLevel == "Sedentary (little or no exercise)") {
                    activity_Input!!.setSelection(0)
                } else if (user.activityLevel == "SLightly active (light exercise/work 1-3 days per week)") {
                    activity_Input!!.setSelection(1)
                } else if (user.activityLevel == "Moderately active (moderate exercise/work 3-5 days per week)") {
                    activity_Input!!.setSelection(2)
                } else if (user.activityLevel == "Very active (hard exercise/work 6-7 days a week)") {
                    activity_Input!!.setSelection(3)
                } else if (user.activityLevel == "Extra active (very hard exercise/work 6-7 days a week)") {
                    activity_Input!!.setSelection(4)
                }

            }
        })


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mStringName = name_Input!!.text.toString()
        mStringAge = age_Input!!.text.toString()
        mHeight = height_Input!!.text.toString()
        mWeight = weight_Input!!.text.toString()
        //mSexual = sex_Input!!.text.toString()
        mCountry = country_Input!!.text.toString()
        mCity = city_Input!!.text.toString()
        //mlvl = activity_Input!!.text.toString()
        mIntake = tv_intake!!.text.toString()

        outState.putString(name_text,mStringName)
        outState.putString(age_text,mStringAge)
        outState.putString(height_text,mHeight)
        outState.putString(weight_text,mWeight)
        outState.putString(sex_text,mSexual)
        outState.putString(country_text,mCountry)
        outState.putString(city_text,mCity)
        outState.putString(activity_text,mlvl)
        outState.putString(activity_textView,mIntake)


        if(imageView!!.drawable != null){
            outState.putParcelable("BITMAP",imageView!!.drawable.toBitmap())
        }
        //outState.putParcelable("BITMAP",mBitmap)
    }

    fun getBitmapFromBytes(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        name_Input!!.setText(savedInstanceState.getString(name_text))
        age_Input!!.setText(savedInstanceState.getString(age_text))
        height_Input!!.setText(savedInstanceState.getString(height_text))
        weight_Input!!.setText(savedInstanceState.getString(weight_text))
        //sex_Input!!.setText(savedInstanceState.getString(sex_text))
        city_Input!!.setText(savedInstanceState.getString(city_text))
        country_Input!!.setText(savedInstanceState.getString(country_text))
        //activity_Input!!.setText(savedInstanceState.getString(activity_text))
        tv_intake!!.text = savedInstanceState.getString(activity_textView)

        if(Build.VERSION.SDK_INT >= 33){
            imageView!!.setImageBitmap(savedInstanceState.getParcelable("BITMAP", Bitmap::class.java))
        }else{
            imageView!!.setImageBitmap(savedInstanceState.getParcelable("BITMAP"))
        }

    }

    override fun onClick(view: View?) {
        when(view?.id){

            R.id.button_calculate ->{
                tv_intake = findViewById(R.id.intake)
                height_Input = findViewById(R.id.et_Height)
                weight_Input = findViewById(R.id.et_Weight)
                sex_Input = findViewById(R.id.et_sex)
                age_Input = findViewById(R.id.et_Age)
                activity_Input = findViewById(R.id.autoComplete_act)

                mHeight = height_Input!!.text.toString()
                mWeight = weight_Input!!.text.toString()
                mSexual = sex_Input!!.selectedItem.toString()
                mStringAge = age_Input!!.text.toString()
                mlvl = activity_Input!!.selectedItem.toString()

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
                var amr = ""
                if(mSexual == "Male"){
                    bmr = String.format("%.3f", 88.362 + (13.397 * weightV) + (4.799 * heightV) - (5.677 * ageV))
                }else if(mSexual == "Female"){
                    bmr = String.format("%.3f", 447.593 + (9.247 * weightV) + (3.098 * heightV) - (4.330 * ageV))
                }

                if(mlvl == "Sedentary (little or no exercise)"){
                    amr = String.format("%.3f", bmr.toDouble() * 1.2)
                }else if(mlvl == "Lightly active (light exercise/work 1-3 days per week)"){
                    amr = String.format("%.3f", bmr.toDouble() * 1.375)
                }else if(mlvl == "Moderately active (moderate exercise/work 3-5 days per week)"){
                    amr = String.format("%.3f", bmr.toDouble() * 1.55)
                }else if(mlvl == "Very active (hard exercise/work 6-7 days a week)"){
                    amr = String.format("%.3f", bmr.toDouble() * 1.725)
                }else if(mlvl == "Extra active (very hard exercise/work 6-7 days a week)"){
                    amr = String.format("%.3f", bmr.toDouble() * 1.9)
                }

                if(mHeight.isNullOrBlank() || mWeight.isNullOrBlank() ||
                    mSexual.isNullOrBlank() || mStringAge.isNullOrBlank() || mlvl.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, "enter all height, weight, age , sex and your activity level", Toast.LENGTH_SHORT).show()

                } else{
                    tv_intake!!.text = amr
                }

            }

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
                imageView = findViewById(R.id.image)

                mStringName = name_Input!!.text.toString()
                mHeight = height_Input!!.text.toString()
                mWeight = weight_Input!!.text.toString()
                mSexual = sex_Input!!.selectedItem.toString()
                mStringAge = age_Input!!.text.toString()
                mCity = city_Input!!.text.toString()
                mCountry = country_Input!!.text.toString()
                mlvl = activity_Input!!.selectedItem.toString()



                if(mStringName.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, "at least enter your name", Toast.LENGTH_SHORT).show()

                }else{
                    imageByteArray = (imageView.drawable as? BitmapDrawable)?.bitmap?.let { bitmap ->
                        getBytesFromBitmap(bitmap)
                    } ?: run {
                        null
                    }
//                    bitmap = (imageView.drawable as BitmapDrawable).bitmap
//                    immutableBitmap = drawable.bitmap
//                    imageByteArray = getBytesFromBitmap(immutableBitmap)
                    // Create a User object and insert it into the database
                    val user = UserData(
                        id  = 1,
                        name = mStringName!!,
                        height = mHeight!!,
                        weight = mWeight!!,
                        sex = mSexual!!,
                        age = mStringAge!!,
                        city = mCity!!,
                        country = mCountry!!,
                        activityLevel = mlvl!!,
                        image = imageByteArray
                    )

                    userViewModel.insertUser(user).observe(this) { userId ->
                        Toast.makeText(this@MainActivity, "User inserted with ID: $userId", Toast.LENGTH_SHORT).show()

                    }
                    Toast.makeText(this@MainActivity, "Welcome!\"", Toast.LENGTH_SHORT).show()
                        val messageIntent = Intent(this, Profile::class.java)

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
                mSexual = sex_Input!!.selectedItem.toString()
                mStringAge = age_Input!!.text.toString()


                var userInserted = false
                val userId = 1 // Replace with the actual user ID
                userViewModel.setUserId(userId)
                userViewModel.getUser(userId).observe(this, Observer { user ->
                 if(user != null && mStringAge == user.age.toString() && mSexual == user.sex
                     && user.height.toString() == mHeight && user.weight.toString() == mWeight){
                     userInserted = true
                 }



                if(!userInserted || mHeight.isNullOrBlank() || mWeight.isNullOrBlank() || mStringAge.isNullOrBlank() || mSexual.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, "enter all height, weight, age and sex and click on profile", Toast.LENGTH_SHORT).show()
                }else{

                    Toast.makeText(this@MainActivity, "Welcome!\"", Toast.LENGTH_SHORT).show()
                    val messageIntent = Intent(this, BMR::class.java)

                    this.startActivity(messageIntent)
                }
                })


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