package com.example.solution_challenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.utils.POST
import com.example.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.instagramclone.utils.uploadImage
import com.example.solution_challenge.databinding.ActivityPostBinding
import com.example.solution_challenge.model.Post
import com.example.solution_challenge.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private var auth = FirebaseAuth.getInstance()
    private lateinit var post: Post
    private lateinit var userDataList: ArrayList<User>
    private lateinit var mDbRef : DatabaseReference
    var imageUrl: String? = null
    var userName: String? = null
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER) { url ->
                if (url != null) {
                    binding.roundedImageView.setImageURI(uri)
                    imageUrl = url
                }
            }

        }
    }

// ...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.roundedImageView.setOnClickListener {
            launcher.launch("image/*")
        }
        post = Post()
        mDbRef = FirebaseDatabase.getInstance().reference

        // Setting name and email
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val userData = postSnapshot.getValue(User::class.java)
                    if (auth.currentUser?.uid == userData?.uid) {
                        userName = userData?.name.toString()

                        // Enable the post button only when the user's name is fetched
                        binding.postBtn.isEnabled = true
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        });

        binding.postBtn.setOnClickListener {
            // Check if userName is not null before proceeding
            if (userName != null) {
                post = Post(
                    imageUrl.toString(),
                    binding.caption.editableText.toString(),
                    userName.toString()
                )
                val postId = Firebase.firestore.collection(POST).document().id
                post.uid = postId  // Assign the generated UID to the post
                Firebase.firestore.collection(POST).document(postId)
                    .set(post)
                    .addOnSuccessListener {
                        startActivity(Intent(this@PostActivity, MainActivity::class.java))
                        finish()
                    }
            }
        }


        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }
    }


}