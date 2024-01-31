package com.example.solution_challenge

import Fragments.HomeFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.utils.DONATE
import com.example.instagramclone.utils.POST
import com.example.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.instagramclone.utils.uploadImage
import com.example.solution_challenge.databinding.ActivityDonateBinding
import com.example.solution_challenge.model.Donate
import com.example.solution_challenge.model.Post
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
    private lateinit var  binding : ActivityDonateBinding
    private lateinit var dataRef : FirebaseDatabase
    private lateinit var  auth : FirebaseAuth
    private lateinit var userName : String
    private lateinit var donate:Donate
    var imageUrl: String? = null
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
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
auth = FirebaseAuth.getInstance()
dataRef = FirebaseDatabase.getInstance()
        dataRef.getReference().child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val userData = postSnapshot.getValue(User::class.java)
                    if (auth.currentUser?.uid == userData?.name) {
                        userName = userData?.name.toString()
                    }
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    donate = Donate()
        binding.btnDonate.setOnClickListener {
            donate = Donate(
             userName.toString(),
                imageUrl.toString(),
            "location",
                binding.description.toString(),
                binding.contactNumber.toString()
             )
            Firebase.firestore.collection(DONATE).document().set(donate).addOnSuccessListener {
                Firebase.firestore.collection(DONATE).document(Firebase.auth.currentUser!!.uid)
                    .set(donate)
                    .addOnSuccessListener {
                        startActivity(Intent(this, HomeFragment::class.java))
                        finish()
                    }
            }
        }



    }
}