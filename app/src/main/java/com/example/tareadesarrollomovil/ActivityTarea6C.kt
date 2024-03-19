package com.example.tareadesarrollomovil

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager

class ActivityTarea6C : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea6_c)

        handleSendTextIntent()
    }

    private fun handleSendTextIntent() {
        if (intent?.action == Intent.ACTION_SENDTO || intent?.action == Intent.ACTION_VIEW) {
            val messageUri = intent.data
            val message = intent.getStringExtra(Intent.EXTRA_TEXT)

            // Extract phone number from the URI
            val phoneNumber = messageUri?.schemeSpecificPart

            // Use SmsManager to send the message
            phoneNumber?.let {
                sendSMS(it, message ?: "")
            }
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
    }
}