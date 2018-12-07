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
        else if (respuesta == "abriendo la calculadora")
        {
            if (vozEscuchada != null) {
                val cal  = getPackageManager().getLaunchIntentForPackage("com.android.calculator2")
                startActivity(cal)
            }
        }
        else if (respuesta == "abriendo el calendario")
        {
            if (vozEscuchada != null) {
                val calen  = getPackageManager().getLaunchIntentForPackage("com.android.calendar")
                startActivity(calen)
            }
        }
        else if (respuesta == "abriendo el navegador")
        {
            if (vozEscuchada != null) {
                val nav  = getPackageManager().getLaunchIntentForPackage("com.android.chrome")
                startActivity(nav)
            }
        }
        else if (respuesta == "abriendo los contactos")
        {
            if (vozEscuchada != null) {
                val con  = getPackageManager().getLaunchIntentForPackage("com.android.contacts")
                startActivity(con)
            }
        }
        else if (respuesta == "abriendo la play store")
        {
            if (vozEscuchada != null) {
                val play  = getPackageManager().getLaunchIntentForPackage("com.android.vending")
                startActivity(play)
            }
        }
        else if (respuesta == "abriendo la grabadora")
        {
            if (vozEscuchada != null) {
                val grab  = getPackageManager().getLaunchIntentForPackage("com.android.soundrecorder")
                startActivity(grab)
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
                val face  = getPackageManager().getLaunchIntentForPackage("com.facebook.android")
                startActivity(face)
            }
        }
        else if (respuesta == "abriendo el reproductor")
        {
            if (vozEscuchada != null) {
                val music  = getPackageManager().getLaunchIntentForPackage("com.android.music")
                startActivity(music)
            }
        }
        else if (respuesta == "abriendo las alarmas")
        {
            if (vozEscuchada != null) {
                val alarm  = getPackageManager().getLaunchIntentForPackage("com.android.deskclock")
                startActivity(alarm)
            }
        }
        else if (respuesta == "abriendo el telefono")
        {
            if (vozEscuchada != null) {
                val tel  = getPackageManager().getLaunchIntentForPackage("com.android.phone")
                startActivity(tel)
            }
        }
        else if (respuesta == "abriendo configuracion del movil")
        {
            if (vozEscuchada != null) {
                val conf  = getPackageManager().getLaunchIntentForPackage("com.android.settings")
                startActivity(conf)
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
        obtenertextos(mensajError, mensajError)
    }
    override fun onListeningCanceled() {

    }
    override fun onListeningFinished() {

    }
    private var capturaVoz : TextToSpeech? = null
    private val token = "6d839590a6244f7baca91a8c44564f99"
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
        val configuracion = AIConfiguration(token, ai.api.AIConfiguration.SupportedLanguages.Spanish,AIConfiguration.RecognitionEngine.System)
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
