package com.example.solution_challenge

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.instagramclone.utils.USER_NODE
import com.example.solution_challenge.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnLogIn: TextView
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var auser:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        editEmail = findViewById(R.id.et_email)
        editPassword = findViewById(R.id.et_password)
        editName = findViewById(R.id.et_name)
        btnSignUp = findViewById(R.id.btn_signUp)
        btnLogIn = findViewById(R.id.btn_logIn)
        mAuth = FirebaseAuth.getInstance()
        auser= User()
        btnSignUp.setOnClickListener {
            val email = editEmail.text.toString()
            val name = editName.text.toString()
            val password = editPassword.text.toString()
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "All Fields are required", Toast.LENGTH_SHORT).show()
            }else
            {
                signUp(name,email,password)
            }
        }

        btnLogIn.setOnClickListener {
            val  intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signUp(name:String,email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    addUserToDatabase(name,email,user?.uid!!)
                    val intent = Intent(this@SignUpActivity,MainActivity::class.java)
//                    Toast.makeText(this,"Added to database",Toast.LENGTH_LONG).show()

                    auser.name = name
                    auser.email = email
                    auser.uid = user.uid
                    //Setting data
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(auser)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").child(uid!!).setValue(User(name, email, uid))

    }
}
