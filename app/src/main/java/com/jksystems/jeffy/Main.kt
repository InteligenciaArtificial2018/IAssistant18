package com.jksystems.jeffy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val btnIzzy = findViewById<ImageView>(R.id.izzbtn)
        val btnJeffy = findViewById<ImageView>(R.id.jeffybtn)

        btnIzzy.setOnClickListener {
            val intent = Intent(this, Izzy::class.java)
            startActivity(intent)
        }

        btnJeffy.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
