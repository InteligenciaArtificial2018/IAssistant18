package com.jksystems.jeffy

import android.app.PendingIntent.getActivity
import android.support.v4.content.ContextCompat.startActivity
import java.security.AccessController.getContext

class ClaseAplicaciones
{
    fun Whatsapp(respuesta: String){

        if (respuesta == "abriendo whatsapp")
        {
            //val contexto = getContext()
            //val wpp  = contexto.getPackageManager().getLaunchIntentForPackage("com.whatsapp")
            //startActivity(wpp)
        }
    }
}