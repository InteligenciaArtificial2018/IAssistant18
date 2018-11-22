package com.jksystems.jeffy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private val VOZ: Int = 0
    var voz: TextView? = null
    var vozescuchada: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val microfono = findViewById<ImageView>(R.id.imageView2)

        microfono.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-HN")
            startActivityForResult(intent, VOZ)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        voz = findViewById(R.id.textView3)
        vozescuchada = findViewById(R.id.textView4)

        if (resultCode == RESULT_OK && requestCode == VOZ)
        {
            arrayListOf<String>() voz = data.getStringArrayList(RecognizerIntent.EXTRA_RESULTS)
            String vozescuchada = voz.get(0)
            voz.setText(vozescuchada)
        }
    }
}
