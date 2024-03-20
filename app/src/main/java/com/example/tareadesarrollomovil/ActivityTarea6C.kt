package com.example.tareadesarrollomovil

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ActivityTarea6C : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea6_c)
        val phoneNumberTextView: TextView = findViewById(R.id.phoneNumberView)
        //si el intent que llamo la actividad es de tipo SENDTO o ACTION_VIEW se ejecuta
        if (intent?.action == Intent.ACTION_SENDTO || intent?.action == Intent.ACTION_VIEW) {
            val messageUri = intent.data
            //se almacena el URI(Uniform Resource Identifier) de la informacion del intent

            // Se extrae el numero de telefono del intent
            val phoneNumber = messageUri?.schemeSpecificPart
            // se escribe el numero de telefono en un text view
            phoneNumberTextView.text = phoneNumber

        }
    }
}