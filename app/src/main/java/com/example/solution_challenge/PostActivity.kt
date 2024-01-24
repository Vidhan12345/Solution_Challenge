package com.example.solution_challenge

import Fragments.HomeFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.utils.POST
import com.example.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.instagramclone.utils.uploadImage
import com.example.solution_challenge.databinding.ActivityPostBinding
import com.example.solution_challenge.model.Post
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class PostActivity : AppCompatActivity() {
    private lateinit var binding :ActivityPostBinding
    private var auth = FirebaseAuth.getInstance()
    private lateinit var post: Post
    var imageUrl:String? = null
     private var launcher = registerForActivityResult(ActivityResultContracts.GetContent())  { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER) {
                    url->
                if (url != null) {
                    binding.roundedImageView.setImageURI(uri)
                    imageUrl = url
                }
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.roundedImageView.setOnClickListener{
            launcher.launch("image/*")
        }
        post= Post()
        binding.postBtn.setOnClickListener {
            post = Post( imageUrl.toString(),
                binding.caption.editableText.toString()
//                auth.currentUser?.displayName.toString()
            )
            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
            Firebase.firestore.collection(POST).document(Firebase.auth.currentUser!!.uid).set(post)
                .addOnSuccessListener {
                    startActivity(Intent(this@PostActivity, HomeFragment::class.java))
                    finish()
                }
        }
        }

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener{
            finish()
        }

    }

}