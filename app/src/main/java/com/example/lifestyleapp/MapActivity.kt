package com.example.lifestyleapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission required to show hikes", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_LOCATION)
        } else {
            mMap.isMyLocationEnabled = true

            // Check if location services are enabled
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show()
                return
            }

            // Get current location
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
                return
            }

            // Mark
            val myLocation = LatLng(location.latitude, location.longitude)
            val markerOptions = MarkerOptions()
                .position(myLocation)
                .title("My Location")
            mMap.addMarker(markerOptions)

            // Show hikes
            val hike1 = LatLng(location.latitude + 0.001, location.longitude + 0.001)
            val hike2 = LatLng(location.latitude - 0.001, location.longitude - 0.001)
            mMap.addMarker(MarkerOptions()
                .position(hike1)
                .title("Nice Hike")
                .snippet("This is a nice hike near you."))
            mMap.addMarker(MarkerOptions()
                .position(hike2)
                .title("Scenic Hike")
                .snippet("This is a scenic hike near you."))

            // Adjust map
            val cameraPosition = CameraPosition.Builder()
                .target(myLocation)
                .zoom(15f)
                .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }

    }

    companion object {
        const val PERMISSIONS_REQUEST_LOCATION = 100
    }
}

