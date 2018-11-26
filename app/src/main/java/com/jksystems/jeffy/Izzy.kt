package com.jksystems.jeffy

import ai.api.AIConfiguration
import ai.api.AIListener
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIResponse
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_izzy.*
import kotlinx.android.synthetic.main.activity_main.*

class Izzy : AppCompatActivity(), AIListener, TextToSpeech.OnInitListener {
    override fun onInit(status: Int) {

    }

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
        val mensajerror = "Hubo un error"
        obtenertextos(mensajerror, mensajerror)
    }

    override fun onListeningCanceled() {

    }

    override fun onListeningFinished() {

    }
    var capturavoz : TextToSpeech? = null
    val tokendeacceso = "fb03ea865c5a48c1801dd6383346fcc4"
    val VOZ = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_izzy)
        validaciondeversion()
        izzy()
        capturavoz = TextToSpeech(this, this)
    }
    fun izzy()
    {
        val configuracion = ai.api.android.AIConfiguration(tokendeacceso, AIConfiguration.SupportedLanguages.Spanish,
            ai.api.android.AIConfiguration.RecognitionEngine.System
        )
        val microfono = AIService.getService(this, configuracion)
        microfono.setListener(this)
        microizzy.setOnClickListener { microfono.startListening()  }
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
        tvescuchandoizzy.text = vozescuchada
        tvrespondiendoizzy.text = respuesta
        respuesta(respuesta)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun respuesta(respuesta: String?)
    {
        capturavoz?.speak(respuesta, TextToSpeech.QUEUE_FLUSH, null, null )
    }
}
