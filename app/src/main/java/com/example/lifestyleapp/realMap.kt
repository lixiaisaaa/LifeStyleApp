package com.example.lifestyleapp

import android.Manifest
import android.annotation.SuppressLint
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
import com.example.lifestyleapp.databinding.ActivityRealMapBinding
import com.google.android.gms.maps.model.CameraPosition

class realMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityRealMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRealMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.isMyLocationEnabled = true

        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }

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

        // Add zoom controls
        mMap.uiSettings.isZoomControlsEnabled = true

        // Adjust map
        val cameraPosition = CameraPosition.Builder()
            .target(myLocation)
            .zoom(16f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}