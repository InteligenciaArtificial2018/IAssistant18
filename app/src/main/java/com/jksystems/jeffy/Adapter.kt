package com.jksystems.jeffy

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlin.coroutines.experimental.coroutineContext

class Adapter(val contexto : Context, val idlayout: Int, val listaregistros:List<Bddizzy>)
    :ArrayAdapter<Bddizzy>(contexto, idlayout, listaregistros) {
    override fun getView(posicion: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view:View = layoutInflater.inflate(idlayout,null)
        val listaizzy = listaregistros[posicion]
        return view
    }
}
