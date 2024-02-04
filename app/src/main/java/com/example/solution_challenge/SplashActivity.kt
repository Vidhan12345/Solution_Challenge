package com.example.solution_challenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.solution_challenge.Intro.Intro1Activity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            if (FirebaseAuth.getInstance().currentUser==null){
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            } else{
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
//                            val intent = Intent(this,SignUpActivity::class.java)
//                startActivity(intent)
            finish()
        },3000)
    }
}