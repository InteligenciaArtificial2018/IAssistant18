package com.jksystems.jeffy

import ai.api.AIConfiguration
import ai.api.AIListener
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIResponse
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
import kotlinx.android.synthetic.main.activity_izzy.*

class Izzy : AppCompatActivity(), AIListener, TextToSpeech.OnInitListener {
    var CapturaVoz : TextToSpeech? = null
    val TokenAcceso = "fb03ea865c5a48c1801dd6383346fcc4"
    val voz = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_izzy)
        validaciondeversion()
        izzy()
        CapturaVoz = TextToSpeech(this, this)
        botonatras()
    }
    fun izzy()
    {
        val configuracion = ai.api.android.AIConfiguration(TokenAcceso, AIConfiguration.SupportedLanguages.Spanish,
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
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), voz)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun obtenertextos(VozEscuchada: String?, respuesta: String?)
    {
        tvescuchandoizzy.text = VozEscuchada
        tvrespondiendoizzy.text = respuesta
        respuesta(respuesta)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun respuesta(respuesta: String?)
    {
        CapturaVoz?.speak(respuesta, TextToSpeech.QUEUE_FLUSH, null, null )
    }
    override fun onInit(status: Int) {

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResult(result: AIResponse?) {
        val resultado = result?.result
        val vozEscuchada = resultado?.resolvedQuery
        val respuesta = resultado?.fulfillment?.speech
        obtenertextos(vozEscuchada, respuesta)
        if (respuesta == "Dejame buscar en la web")
        {
            val url = "http://www.google.com/search?q=$vozEscuchada!!"
            val uri = Uri.parse(url)
            val web = Intent(Intent.ACTION_VIEW, uri)
            startActivity(web)
        }
        else if (respuesta == "abriendo whatsapp")
        {
            if (vozEscuchada != null) {
                val wpp  = getPackageManager().getLaunchIntentForPackage("com.whatsapp")
                startActivity(wpp)
            }
        }
        else if (respuesta == "abriendo youtube")
        {
            if (vozEscuchada != null) {
                val you  = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube")
                startActivity(you)
            }
        }
        else if (respuesta == "abriendo el navegador")
        {
            if (vozEscuchada != null) {
                val nav  = getPackageManager().getLaunchIntentForPackage("com.android.chrome")
                startActivity(nav)
            }
        }
        else if (respuesta == "abriendo la play store")
        {
            if (vozEscuchada != null) {
                val play  = getPackageManager().getLaunchIntentForPackage("com.android.vending")
                startActivity(play)
            }
        }
        else if (respuesta == "abriendo instagram")
        {
            if (vozEscuchada != null) {
                val insta  = getPackageManager().getLaunchIntentForPackage("com.instagram.android")
                startActivity(insta)
            }
        }
        else if (respuesta == "abriendo facebook")
        {
            if (vozEscuchada != null) {
                val face  = getPackageManager().getLaunchIntentForPackage("com.facebook.katana")
                startActivity(face)
            }
        }
    }

    override fun onListeningStarted() {

    }

    override fun onAudioLevel(level: Float) {

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onError(error: AIError?) {
        val mensajError = "Despacio, ha ocurrido un error"
        //Toast.makeText(this, "Conectate a una red", Toast.LENGTH_SHORT).show()
        obtenertextos(mensajError, mensajError)
    }

    override fun onListeningCanceled() {

    }

    override fun onListeningFinished() {

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
