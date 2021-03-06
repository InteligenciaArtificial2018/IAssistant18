package com.jksystems.jeffy

// Metodos importados del AI, esto viene de una implementacion en las dependencias
import ai.api.AIConfiguration
import ai.api.AIListener
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIResponse

// Importe de las super clases de Android
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
import kotlinx.android.synthetic.main.activity_izzy.*

// Clase del agente Izzy
class Izzy : AppCompatActivity(), AIListener, TextToSpeech.OnInitListener {
    // Esta variable acciona el discurso que nos devuelve el agente
    var CapturaVoz : TextToSpeech? = null
    // Esta variable nos declara la llave del agente que nos ofrece DIALOGFLOW
    val TokenAcceso = "fb03ea865c5a48c1801dd6383346fcc4"
    // Esta variable controla el requestcode que necesita la aplicacion para arrojar un resultado
    val voz = 1

    // Instancia OnCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_izzy)

        // Funcion que valida la SDK del telefono
        validaciondeversion()

        // Funcion que obtiene los servicios de AIConfiguration
        izzy()

        // Variable que obtiene lo que le decimos al agente
        CapturaVoz = TextToSpeech(this, this)

        // Funcion que devuelve el activity hacia atras
        botonatras()
    }
    fun izzy()
    {
        // Esta variable define el token de acceso del agente y el idioma con el cual se va a interactuar
        val configuracion = ai.api.android.AIConfiguration(TokenAcceso, AIConfiguration.SupportedLanguages.Spanish,
            ai.api.android.AIConfiguration.RecognitionEngine.System)
        // Esta variable funciona como microfono
        val microfono = AIService.getService(this, configuracion)

        // El microfono escucha lo que el usuario dice
        microfono.setListener(this)

        // Al hacer click en el microfono, comienza a escuchar
        microizzy.setOnClickListener { microfono.startListening()  }
    }

    fun validaciondeversion()
    {
        // Si la SDK del telefono es superior a LOLLIPOP, la aplicacion dara permiso para ejecutar sus servicios
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        {
            permiso()
        }
    }

    fun permiso()
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
        tvescuchandoizzy.text = VozEscuchada
        tvrespondiendoizzy.text = respuesta
        respuesta(respuesta)
    }

    // Esta anotacion despliega un consejo sobre que SDK es mas apropiado para la app
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)


    fun respuesta(respuesta: String?)
    {
        // Captura la respuesta del agente y acciona su discurso para que el usuario la pueda escuchar
        CapturaVoz?.speak(respuesta, TextToSpeech.QUEUE_FLUSH, null, null )
    }
    override fun onInit(status: Int) {
        // Metodo vacio obligatorio de AIservices
    }

    // Esta anotacion despliega un consejo sobre que SDK es mas apropiado para la app
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
        val bdd = FirebaseDatabase.getInstance().getReference("Izzy")

        // Obtenemos los constructores de preguntas y respuestas
        val izzyPreguntas = Preguntas(vozEscuchada!!)
        val izzyRespuestas = Respuestas(respuesta!!)

        // Insertamos la pregunta y la respuesta en la base de datos
        bdd.child("P:").setValue(izzyPreguntas)
        bdd.child("R:").setValue(izzyRespuestas)

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

    // Esta anotacion despliega un consejo sobre que SDK es mas apropiado para la app
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onError(error: AIError?) {
        val mensajError = "Despacio, ha ocurrido un error"
        //Toast.makeText(this, "Conectate a una red", Toast.LENGTH_SHORT).show()
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
