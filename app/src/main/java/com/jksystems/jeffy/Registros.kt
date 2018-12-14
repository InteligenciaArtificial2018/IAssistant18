package com.jksystems.jeffy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_registros.*

class Registros : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)
        val bdd = FirebaseDatabase.getInstance().getReference()
        val registrosizzy = Main().visitaIzzy
        val registrosjeffy = Main().visitaJeffy
        val regizzy = findViewById<TextView>(R.id.regizzi)
        val regjeffy = findViewById<TextView>(R.id.regijeffi)

        regizzy.setText(registrosizzy.toString())
        regjeffy.setText(registrosjeffy.toString())
    }
    fun botonatras ()
    {
        val atras = supportActionBar
        if (atras != null)
        {
            atras.setDisplayHomeAsUpEnabled(true)
        }
    }
}
