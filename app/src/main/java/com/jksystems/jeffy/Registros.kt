package com.jksystems.jeffy

// Importaciones de Android
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.ListView
import android.widget.TextView

// Importacion de FireBase
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registros.*

class Registros : AppCompatActivity() {
    // onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)

        // Instancia de la base de datos
        val bdd = FirebaseDatabase.getInstance().getReference()

        // Llamamos los registros desde el main
        val registrosizzy = Main().visitaIzzy
        val registrosjeffy = Main().visitaJeffy

        // Textviews
        val regizzy = findViewById<TextView>(R.id.regizzi)
        val regjeffy = findViewById<TextView>(R.id.regijeffi)

        // Insertamos la info de la base en los textview
        regizzy.setText(registrosizzy.toString())
        regjeffy.setText(registrosjeffy.toString())

        // llamamos la funcion boton atras
        botonatras()
    }

    // Funcion que retorna el activity al Main
    fun botonatras ()
    {
        val atras = supportActionBar
        if (atras != null)
        {
            atras.setDisplayHomeAsUpEnabled(true)
        }
    }
}
