package com.example.solution_challenge

import Fragments.HomeFragment
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.instagramclone.utils.DONATE
import com.example.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.instagramclone.utils.uploadImage
import com.example.solution_challenge.databinding.ActivityDonateBinding
import com.example.solution_challenge.model.Donate
import com.example.solution_challenge.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore

class DonateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonateBinding
    private lateinit var dataRef: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userName: String
    private lateinit var donate: Donate

    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private var imageUrl: String? = null

    private var launcher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uploadImage(uri, USER_PROFILE_FOLDER) { url ->
                    if (url != null) {
                        binding.donateImg.setImageURI(uri)
                        imageUrl = url
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDonateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDonate.setOnClickListener {
            launcher.launch("image/*")
        }

        userName = ""
        auth = FirebaseAuth.getInstance()
        dataRef = FirebaseDatabase.getInstance()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Initialize locationListener
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                currentLatitude = location.latitude
                currentLongitude = location.longitude

                // Handle location updates here
                val latitude = location.latitude
                val longitude = location.longitude
//                Toast.makeText(
//                    this@DonateActivity,
//                    "Latitude: $latitude, Longitude: $longitude",
//                    Toast.LENGTH_SHORT
//                ).show()
            }


        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_ACCESS_FINE_LOCATION
            )
        } else {
            // Start location updates
            getLocation()
        }
        dataRef.getReference().child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val userData = postSnapshot.getValue(User::class.java)
                    if (auth.currentUser?.uid == userData?.name) {
                        userName = userData?.name.toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })

        donate = Donate()
        binding.btnDonate.setOnClickListener {
            val location = "$currentLatitude,$currentLongitude"
            Toast.makeText(this, "My Location $location", Toast.LENGTH_SHORT).show()
            donate = Donate(
                userName,
                imageUrl.toString(),
                location,
                binding.description.toString(),
                binding.contactNumber.toString()
            )
            Firebase.firestore.collection(DONATE).document().set(donate).addOnSuccessListener {
                Firebase.firestore.collection(DONATE).document(Firebase.auth.currentUser!!.uid)
                    .set(donate)
                    .addOnSuccessListener {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
            }
        }



    }
    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_ACCESS_FINE_LOCATION
            )
            return
        }

        // Start requesting location updates
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            0L,
            0f,
            locationListener
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION) {
            when (grantResults[0]) {
                PackageManager.PERMISSION_GRANTED -> getLocation()
                PackageManager.PERMISSION_DENIED -> Toast.makeText(
                    this,
                    "You need to Grant Permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    companion object {
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
    }
}
