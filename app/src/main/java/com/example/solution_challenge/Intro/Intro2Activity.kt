package com.example.solution_challenge.Intro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.solution_challenge.LoginActivity
import com.example.solution_challenge.R
import com.example.solution_challenge.SignUpActivity

class Intro2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro2)

        val start = findViewById<Button>(R.id.startBt)
        start.setOnClickListener {
            val  intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}