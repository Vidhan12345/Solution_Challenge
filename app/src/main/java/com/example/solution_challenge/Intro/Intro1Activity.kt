package com.example.solution_challenge.Intro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.solution_challenge.R

class Intro1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro1)

        val next = findViewById<Button>(R.id.intro1bt)
        next.setOnClickListener{

            startActivity(Intent(this,Intro2Activity::class.java))
        }
    }
}