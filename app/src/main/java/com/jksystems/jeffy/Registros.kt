package com.jksystems.jeffy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.*

class Registros : AppCompatActivity() {

    lateinit var bdd: DatabaseReference
    lateinit var cargaLista: MutableList<Bddizzy>
    lateinit var lista: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)

        cargaLista = mutableListOf()
        lista = findViewById(R.id.listregistros)
        bdd = FirebaseDatabase.getInstance().getReference("jeffy-7c811")

        bdd.addValueEventListener(object :  ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                cargaLista.clear()
                if (p0.exists()){
                    for (i in p0.children)
                    {
                        val visitas = i.getValue(Bddizzy::class.java)
                        cargaLista.add(visitas!!)
                    }

                }
            }
        })

        botonatras()
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
