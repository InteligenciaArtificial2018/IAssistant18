package com.jksystems.jeffy

// Importaciones de Android
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

// Importacion de la base de datos de FireBase
import com.google.firebase.database.FirebaseDatabase

class Main : AppCompatActivity() {
    // Variable que cuenta las visitas de los agentes
    var visitaJeffy = 0
    var visitaIzzy = 0

    // onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Llamamos los botones y el textview
        val btnIzzy = findViewById<ImageView>(R.id.izzbtn)
        val btnJeffy = findViewById<ImageView>(R.id.jeffybtn)

        // Click en el agente Izzy
        btnIzzy.setOnClickListener {
            // Verifica si hay conexion a internet antes de entrar al activiy
            if (VerificarConexion.verificarConexion(this))
            {
                // Contador de las visitas
                visitaIzzy += 1

                // Instancia de la base
                val bdd = FirebaseDatabase.getInstance().getReference()

                // Dato que sera insertado en la base de datos
                val izzyIngreso = Bddizzy(visitaIzzy)

                // Insercion de los datos en la base
                bdd.child("Izzy").setValue(izzyIngreso)

                // Intent para acceder al activity
                val intent = Intent(this, Izzy::class.java)
                startActivity(intent)
            }
            else
            {
                // Mensaje de error si no hay conexion a internet
                Toast.makeText(this, "¡Verifica tu conexión a Internet!", Toast.LENGTH_SHORT).show()
            }
        }

        // Click en el agente Jeffy
        btnJeffy.setOnClickListener {
            // Verifica si hay conexion a internet antes de entrar al activiy
            if (VerificarConexion.verificarConexion(this))
            {
                // Contador de las visitas
                visitaJeffy += 1

                // Instancia de la base
                val bdd = FirebaseDatabase.getInstance().getReference()

                // Dato que sera insertado en la base de datos
                val jeffyIngreso = Bddjeffy(visitaJeffy)

                // Dato que sera insertado en la base de datos
                bdd.child("Jeffy").setValue(jeffyIngreso)

                // Intent para acceder al activity
                val intent = Intent(this, Jeffy::class.java)
                startActivity(intent)
            }
            else
            {
                // Mensaje de error si no hay conexion a internet
                Toast.makeText(this, "¡Verifica tu conexión a Internet!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


