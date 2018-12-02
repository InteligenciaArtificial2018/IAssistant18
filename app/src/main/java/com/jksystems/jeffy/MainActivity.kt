package com.jksystems.jeffy

import ai.api.AIListener
import ai.api.android.AIConfiguration
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIResponse
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AIListener, TextToSpeech.OnInitListener {
    override fun onInit(status: Int) {

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResult(result: AIResponse?) {
        val resultado = result?.result
        val vozEscuchada = resultado?.resolvedQuery
        val respuesta = resultado?.fulfillment?.speech
        obtenertextos(vozEscuchada, respuesta)
        if (respuesta == "Dejame buscar en la web")
        {
            val URL = "http://www.google.com/search?q=$vozEscuchada!!"
            val Uri = Uri.parse(URL)
            val web = Intent(Intent.ACTION_VIEW, Uri)
            startActivity(web)
        }
        else if (respuesta == "abriendo whatsapp")
        {
            val wpp = ClaseAplicaciones()
            wpp.Whatsapp()
        }
    }
    override fun onListeningStarted() {

    }
    override fun onAudioLevel(level: Float) {

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onError(error: AIError?) {
        val MensajError = "Despacio, ha ocurrido un error"
        obtenertextos(MensajError, MensajError)
    }
    override fun onListeningCanceled() {

    }
    override fun onListeningFinished() {

    }
    private var capturaVoz : TextToSpeech? = null
    private val Token = "6d839590a6244f7baca91a8c44564f99"
    private val voz = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        validaciondeversion()
        jeffy()
        capturaVoz = TextToSpeech(this, this)
        botonatras()
    }

    fun jeffy()
    {
        val configuracion = AIConfiguration(Token, ai.api.AIConfiguration.SupportedLanguages.Spanish,AIConfiguration.RecognitionEngine.System)
        val microfono = AIService.getService(this, configuracion)
        microfono.setListener(this)
        microjeffy.setOnClickListener { microfono.startListening()  }
    }

    private fun validaciondeversion()
    {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            {
                permiso()
            }
    }

    private fun permiso()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), voz)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun obtenertextos(VozEscuchada: String?, respuesta: String?)
    {
        tvescuchandojeffy.text = VozEscuchada
        tvrespondiendojeffy.text = respuesta
        respuesta(respuesta)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun respuesta(respuesta: String?)
    {
        capturaVoz?.speak(respuesta, TextToSpeech.QUEUE_FLUSH, null, null )
    }

    @SuppressLint("RestrictedApi")
    fun botonatras ()
    {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
