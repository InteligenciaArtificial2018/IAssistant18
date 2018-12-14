package com.jksystems.jeffy

// Metodos importados del AI, esto viene de una implementacion en las dependencias
import ai.api.AIListener
import ai.api.android.AIConfiguration
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIResponse

// Importe de las super clases de Android
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

// Importe de las dependencias de Firebase
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_jeffy.*

// Clase del agente Jeffy
class Jeffy : AppCompatActivity(), AIListener, TextToSpeech.OnInitListener {
    // Esta variable acciona el discurso que nos devuelve el agente
    private var capturaVoz : TextToSpeech? = null
    // Esta variable nos declara la llave del agente que nos ofrece DIALOGFLOW
    private val token = "6d839590a6244f7baca91a8c44564f99"
    // Esta variable controla el requestcode que necesita la aplicacion para arrojar un resultado
    private val voz = 1

    // Instancia OnCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jeffy)

        // Funcion que valida la SDK del telefono
        validaciondeversion()

        // Funcion que obtiene los servicios de AIConfiguration
        jeffy()

        // Variable que obtiene lo que le decimos al agente
        capturaVoz = TextToSpeech(this, this)

        // Funcion que devuelve el activity hacia atras
        botonatras()
    }

    fun jeffy()
    {
        // Esta variable define el token de acceso del agente y el idioma con el cual se va a interactuar
        val configuracion = AIConfiguration(token, ai.api.AIConfiguration.SupportedLanguages.Spanish,AIConfiguration.RecognitionEngine.System)
        val microfono = AIService.getService(this, configuracion)

        // Esta variable funciona como microfono
        microfono.setListener(this)

        // Al hacer click en el microfono, comienza a escuchar
        microjeffy.setOnClickListener { microfono.startListening()  }
    }

    private fun validaciondeversion()
    {
        // Si la SDK del telefono es superior a LOLLIPOP, la aplicacion dara permiso para ejecutar sus servicios
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        {
            permiso()
        }
    }

    private fun permiso()
    {
        // Obtiene los permisos para poder grabar voz
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), voz)
        }
    }

    // Esta anotacion despliega un consejo sobre que SDK es mas apropiado para la app
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    // Setea en los textview la pregunta del usuario y la respuesta del agente
    fun obtenertextos(VozEscuchada: String?, respuesta: String?)
    {
        tvescuchandojeffy.text = VozEscuchada
        tvrespondiendojeffy.text = respuesta
        respuesta(respuesta)
    }

    // Esta anotacion despliega un consejo sobre que SDK es mas apropiado para la app
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)


    fun respuesta(respuesta: String?)
    {
        // Captura la respuesta del agente y acciona su discurso para que el usuario la pueda escuchar
        capturaVoz?.speak(respuesta, TextToSpeech.QUEUE_FLUSH, null, null )
    }
    override fun onInit(status: Int) {
        // Metodo vacio obligatorio de AIservices
    }

    // Esta anotacion despliega un consejo sobre que SDK es mas apropiado para la app
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    // Metodo de AIservices que inicia cuando el agente termina de hablaronos
    override fun onResult(result: AIResponse?) {

        // variable que obtiene si el proceso de preguntar y escuchar ha sido exitoso
        val resultado = result?.result

        // Variable que obtiene lo que le decimos al agente
        val vozEscuchada = resultado?.resolvedQuery

        // Variable que obtiene lo que nos contesta el agente
        val respuesta = resultado?.fulfillment?.speech

        // LLamamos a esta funcion para llenar los textview
        obtenertextos(vozEscuchada, respuesta)

        // Instanciamos la base de datos de FireBase
        val bdd = FirebaseDatabase.getInstance().getReference("Jeffy")

        // Obtenemos los constructores de preguntas y respuestas
        val jeffyPreguntas = Preguntas(vozEscuchada!!)
        val jeffyRespuestas = Respuestas(respuesta!!)

        // Insertamos la pregunta y la respuesta en la base de datos
        bdd.child("P:").setValue(jeffyPreguntas)
        bdd.child("R:").setValue(jeffyRespuestas)

        // Los siguientes "ifs" abren aplicaciones
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
        else if (respuesta == "abriendo los registros")
        {
            if (vozEscuchada != null) {
                val intent = Intent(this, Registros::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onListeningStarted() {
        // Metodo de AIservices que se ejecuta cuando se comienza a escuchar
    }

    override fun onAudioLevel(level: Float) {
        // Metodo de AIservices que se ejecuta con el volumen de la aplicacion
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    // Mensaje cuando la app da error
    override fun onError(error: AIError?) {
        val mensajError = "Despacio, ha ocurrido un error"
        obtenertextos(mensajError, mensajError)
    }

    override fun onListeningCanceled() {
        // Metodo de AIservices que se cancela el proceso de escuchar
    }
    override fun onListeningFinished() {
        // Metodo de AIservices que se ejecuta con termina de escuchar
    }

    // Retorna el activity hacia atras
    fun botonatras ()
    {
        // llama los servicios del actionbar
        val atras = supportActionBar
        if (atras != null)
        {
            // devuelve el activity
            atras.setDisplayHomeAsUpEnabled(true)
        }
    }
}
