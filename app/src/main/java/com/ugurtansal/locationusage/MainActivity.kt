package com.ugurtansal.locationusage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.ugurtansal.locationusage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var permissionControl=0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonLocation.setOnClickListener {

            permissionControl= ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) //Control permission for access fine location

            if(permissionControl== PackageManager.PERMISSION_GRANTED){ //If permission is granted
                //requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1) //Request permission
            }
            else{
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100) //100 is the request code , can change it, We will use it in onRequestPermissionsResult


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
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //If permission is granted
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show() //Show message
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show() //Show message
            }
        }
    }
}