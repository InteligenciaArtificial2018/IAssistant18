package com.jksystems.jeffy

import ai.api.AIListener
import ai.api.android.AIConfiguration
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIResponse
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AIListener, TextToSpeech.OnInitListener {
    override fun onInit(status: Int) {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResult(result: AIResponse?) {
        val resultado = result?.result
        val vozescuchada = resultado?.resolvedQuery
        val respuesta = resultado?.fulfillment?.speech
        obtenertextos(vozescuchada, respuesta)
    }

    override fun onListeningStarted() {

    }

    override fun onAudioLevel(level: Float) {

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onError(error: AIError?) {
        val mensajerror = "Debes conectarte a la red Wi-fi, o activar tus datos"
        Toast.makeText(this, "Conectate a una red", Toast.LENGTH_SHORT).show()
        obtenertextos(mensajerror, mensajerror)
    }

    override fun onListeningCanceled() {

    }

    override fun onListeningFinished() {

    }
    var capturavoz : TextToSpeech? = null
    val tokendeacceso = "6d839590a6244f7baca91a8c44564f99"
    val VOZ = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        validaciondeversion()
        jeffy()
        capturavoz = TextToSpeech(this, this)
        botonatras()
    }

    fun jeffy()
    {
        val configuracion = AIConfiguration(tokendeacceso, ai.api.AIConfiguration.SupportedLanguages.Spanish,AIConfiguration.RecognitionEngine.System)
        val microfono = AIService.getService(this, configuracion)
        microfono.setListener(this)
        microjeffy.setOnClickListener { microfono.startListening()  }
    }

    fun validaciondeversion()
    {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            {
                permiso()
            }
    }

    fun permiso()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), VOZ)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun obtenertextos(vozescuchada: String?, respuesta: String?)
    {
        tvescuchandojeffy.text = vozescuchada
        tvrespondiendojeffy.text = respuesta
        respuesta(respuesta)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun respuesta(respuesta: String?)
    {
        capturavoz?.speak(respuesta, TextToSpeech.QUEUE_FLUSH, null, null )
    }

    @SuppressLint("RestrictedApi")
    fun botonatras ()
    {
        val atras = supportActionBar
        if (atras != null)
        {
            atras.setDisplayHomeAsUpEnabled(true)
        }
    }
}
