package com.jksystems.jeffy

// Importaciones de Android
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splash : AppCompatActivity() {

    // variable que detecta si el splash esta corriendo
    var elhandler : Handler? = null

    // variable que controla el tiempo que el splash se proyectara
    val tiempo : Long = 3100

    // variable que controla si el layout corre
    val corriendo: Runnable = Runnable()
    {
        if (!isFinishing)
        {
            // Cuando termina de correr abre el main
            val intent = Intent(applicationContext, Main::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Instancia del Handler
        elhandler = Handler()
        elhandler!!.postDelayed(corriendo, tiempo)
    }

    override fun onDestroy() {
        if (true)
        {
            elhandler!!.removeCallbacks(corriendo)
        }
        super.onDestroy()
    }
}
