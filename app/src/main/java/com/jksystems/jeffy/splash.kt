package com.jksystems.jeffy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splash : AppCompatActivity() {
    var elhandler : Handler? = null
    val tiempo : Long = 3000
    val corriendo: Runnable = Runnable()
    {
        if (!isFinishing)
        {
            val intent = Intent(applicationContext, Main::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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
