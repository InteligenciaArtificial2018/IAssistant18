package com.jksystems.jeffy

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity

class VerificarConexion {
    companion object {
        fun verificarConexion(activity: AppCompatActivity): Boolean {
            val conectar = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val InfoRed = conectar.activeNetworkInfo
            return InfoRed != null && InfoRed.isConnected
        }
    }
}