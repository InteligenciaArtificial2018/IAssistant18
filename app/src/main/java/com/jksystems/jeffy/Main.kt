package com.jksystems.jeffy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class Main : AppCompatActivity() {
    var visitaJeffy = 0
    var visitaIzzy = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val btnIzzy = findViewById<ImageView>(R.id.izzbtn)
        val btnJeffy = findViewById<ImageView>(R.id.jeffybtn)
        val registros = findViewById<TextView>(R.id.textView5)

        registros.setOnClickListener {
            val intent = Intent(this, Registros::class.java)
            startActivity(intent)
        }
        btnIzzy.setOnClickListener {
            if (VerificarConexion.verificarConexion(this))
            {
                visitaIzzy += 1
                val bdd = FirebaseDatabase.getInstance().getReference()
                val izzyIngreso = Bddizzy(visitaIzzy)
                bdd.child("Izzy").setValue(izzyIngreso)
                val intent = Intent(this, Izzy::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this, "¡Verifica tu conexión a Internet!", Toast.LENGTH_SHORT).show()
            }
        }
        btnJeffy.setOnClickListener {
            if (VerificarConexion.verificarConexion(this))
            {
                visitaJeffy += 1
                val bdd = FirebaseDatabase.getInstance().getReference()
                val jeffyIngreso = Bddjeffy(visitaJeffy)
                bdd.child("Jeffy").setValue(jeffyIngreso)
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


