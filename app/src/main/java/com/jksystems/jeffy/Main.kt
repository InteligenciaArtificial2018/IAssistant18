package com.jksystems.jeffy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val BtnIzzy = findViewById<ImageView>(R.id.izzbtn)
        val BtnJeffy = findViewById<ImageView>(R.id.jeffybtn)

        BtnIzzy.setOnClickListener {
            if (VerificarConexion.verificarConexion(this))
            {
                val intent = Intent(this, Izzy::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this, "¡Verifica tu conexión a Internet!", Toast.LENGTH_SHORT).show()

            }
        }

        BtnJeffy.setOnClickListener {
            if (VerificarConexion.verificarConexion(this))
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this, "¡Verifica tu conexión a Internet!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
