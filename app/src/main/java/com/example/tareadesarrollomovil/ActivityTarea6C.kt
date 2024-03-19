package com.example.tareadesarrollomovil

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ActivityTarea6C : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea6_c)
        val phoneNumberTextView: TextView = findViewById(R.id.phoneNumberView)

        if (intent?.action == Intent.ACTION_SENDTO || intent?.action == Intent.ACTION_VIEW) {
            val messageUri = intent.data
            //val message = intent.getStringExtra(Intent.EXTRA_TEXT)

            // Extract phone number from the URI
            val phoneNumber = messageUri?.schemeSpecificPart

            phoneNumberTextView.text = phoneNumber

        }
    }
}