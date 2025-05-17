package com.ugurtansal.locationusage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.ugurtansal.locationusage.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity() , OnMapReadyCallback{
    private lateinit var binding: ActivityMainBinding
    private var permissionControl=0;

    private lateinit var flpc: FusedLocationProviderClient
    private lateinit var locationTask: Task<Location>
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        flpc= LocationServices.getFusedLocationProviderClient(this)


        binding.buttonLocation.setOnClickListener {

            permissionControl= ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) //Control permission for access fine location

            if(permissionControl== PackageManager.PERMISSION_GRANTED){ //If permission is granted

                locationTask= flpc.getLastLocation() //Get last location
                getLocationInfo() //Get location info
            }
            else{
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100) //100 is the request code , can change it, We will use it in onRequestPermissionsResult


            }
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

            binding.buttonGoLocation.setOnClickListener {
                val location = LatLng(41.0361566, 28.9854576)
                mMap.addMarker(MarkerOptions()
                    .position(location)
                    .title("My Location"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,17f))
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }


        }



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val konum = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions()
            .position(konum)
            .title("Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(konum))
    }



    fun getLocationInfo() { //Get location info
        locationTask.addOnSuccessListener {
            if(it!=null){ //If location is not null
                binding.textViewLatitude.text="Latitude: ${it.latitude}" //Set latitude to textView
                binding.textViewlongitude.text="Longitude ${it.longitude}" //Set longitude to textView
            }
            else{
                binding.textViewLatitude.text="Latitude error" //Set latitude to textView
                binding.textViewlongitude.text="Longitude error" //Set longitude to textView
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == 100) { //If request code is 100
            permissionControl= ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) //Control permission for access fine location
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //If permission is granted
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show() //Show message
                locationTask= flpc.getLastLocation() //Get last location
                getLocationInfo() //Get location info
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show() //Show message
            }
        }
    }
}




