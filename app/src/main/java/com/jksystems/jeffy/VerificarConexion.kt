package com.jksystems.jeffy

// Importaciones de Android

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity

class VerificarConexion {
    companion object {
        fun verificarConexion(activity: AppCompatActivity): Boolean {

            // Variable que obtiene el estado de la red
            val conectar = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val InfoRed = conectar.activeNetworkInfo

            // Devolvemos que la red esta conectada
            return InfoRed != null && InfoRed.isConnected
        }
    }
}