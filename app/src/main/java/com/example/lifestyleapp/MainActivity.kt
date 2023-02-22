package com.example.lifestyleapp

import android.Manifest
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
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap


class MainActivity : AppCompatActivity() ,View.OnClickListener{
    //updated
    private var age_Input: EditText? = null
    private var name_Input: EditText? = null
    private var height_Input: EditText? = null
    private var weight_Input: EditText? = null
    private var mButtonCamera: Button? = null
    private var mButtonSubmit: Button? = null
    private var mButtonHikes: Button? = null

    //pics
    private lateinit var takePicLauncher: ActivityResultLauncher<String>
    private lateinit var imageView: ImageView

    //Data store
    private var mStringName: String? = null
    private var mStringAge: String? = null
    private var mHeight: String? = null
    private var mWeight: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        age_Input = findViewById(R.id.et_Age)
        name_Input = findViewById(R.id.et_Name)
        height_Input = findViewById(R.id.et_Height)
        weight_Input = findViewById(R.id.et_Weight)
        imageView = findViewById(R.id.image)

        mButtonCamera = findViewById(R.id.button_pic)
        mButtonSubmit = findViewById(R.id.button_submit)
        mButtonHikes = findViewById(R.id.button_hikes)

        takePicLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            Log.d("MainActivity", "onCreate: uri=$it, ")
            imageView.setImageURI(it)
        }

        mButtonCamera!!.setOnClickListener(this)
        mButtonSubmit!!.setOnClickListener(this)

        imageView.setOnClickListener {
            takePicLauncher.launch("image/*")
        }

        mButtonHikes!!.setOnClickListener {
            // request permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION)
            } else {
                // User has already granted location permission, open MapActivity
                startActivity(Intent(this, realMap::class.java))
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mStringName = name_Input!!.text.toString()
        mStringAge = age_Input!!.text.toString()
        mHeight = height_Input!!.text.toString()
        mWeight = weight_Input!!.text.toString()


        outState.putString("NM_TEXT",mStringName)
        outState.putString("AG_TEXT",mStringAge)
        outState.putString("HT_TEXT",mHeight)
        outState.putString("WT_TEXT",mWeight)


        if(imageView!!.drawable != null){
            outState.putParcelable("BITMAP",imageView!!.drawable.toBitmap())
        }
        //outState.putParcelable("BITMAP",mBitmap)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        name_Input!!.setText(savedInstanceState.getString("NM_TEXT"))
        age_Input!!.setText(savedInstanceState.getString("AG_TEXT"))
        height_Input!!.setText(savedInstanceState.getString("HT_TEXT"))
        weight_Input!!.setText(savedInstanceState.getString("WT_TEXT"))


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
            R.id.button_submit ->{
                height_Input = findViewById(R.id.et_Height)
                weight_Input = findViewById(R.id.et_Weight)

                mHeight = height_Input!!.text.toString()
                mWeight = weight_Input!!.text.toString()

                if(mHeight.isNullOrBlank() || mWeight.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, "enter both height and weight", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity, "Welcome!\"", Toast.LENGTH_SHORT).show()

                    val messageIntent = Intent(this, BMI::class.java)
                    messageIntent.putExtra("HT_STRING", mHeight)
                    messageIntent.putExtra("WT_STRING", mWeight)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, open MapActivity
                startActivity(Intent(this, MapActivity::class.java))
            } else {
                Toast.makeText(this, "Location permission required to show hikes", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object {
        const val PERMISSIONS_REQUEST_LOCATION = 100
    }

}