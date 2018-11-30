package com.jksystems.jeffy


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity

class Navegador(URL: String) {
    init
    {
        val Uri = Uri.parse(URL)
        val web = Intent(Intent.ACTION_VIEW, Uri)
        val context = MainActivity()
        startActivity(context, web, Bundle())
    }
}
