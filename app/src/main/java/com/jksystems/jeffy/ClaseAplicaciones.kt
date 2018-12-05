package com.jksystems.jeffy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.content.ComponentName



class ClaseAplicaciones
{
    fun Whatsapp(): Unit {
        val contexto = Main()
        val wpp = Intent("android.intent.action.MAIN")
        wpp.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
        startActivity(contexto, wpp, Bundle())
    }
}