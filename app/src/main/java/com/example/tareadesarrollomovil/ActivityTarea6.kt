package com.example.tareadesarrollomovil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

var savedPicture: Uri? = null

class ActivityTarea6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea6)
        val sendTextButton: Button = findViewById(R.id.sendTextButton)
        val editTextUserInput = findViewById<EditText>(R.id.inputText)
        val takePhotoButton: Button = findViewById(R.id.takePhotoButton)

        sendTextButton.setOnClickListener{
            val userInput = editTextUserInput.text.toString()
            val sendIntent = Intent()
            // se configura el intent para que la accion sea de tipo SEND, para mandar datos a otra app
            sendIntent.setAction(Intent.ACTION_SEND)
            //se manda el msj como EXTRA_TEXT con el intent
            sendIntent.putExtra(Intent.EXTRA_TEXT, userInput)
            // se configura el tipo de datos que llevara el intent
            sendIntent.setType("text/plain")
            // Checar si existe una app que pueda recibir los datos
            if (sendIntent.resolveActivity(packageManager) != null) {
                // mandar los datos
                startActivity(Intent.createChooser(sendIntent, "Choose an app"))
            }
        }

        takePhotoButton.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onPause() {
        super.onPause()
        Log.d("Tarea6Debug", "onPause")
    }

    override fun onResume() {
        super.onResume()

        val image:ImageView = findViewById(R.id.imagenIncisoDos)

        Log.d("Tarea6Debug", "onResume")
        if(savedPicture != null) {
            Log.d("Tarea6Debug", "Found URI at "+ savedPicture.toString())
            image.setImageURI(savedPicture)
        }else{
            Log.d("Tarea6Debug", "No URI found")
        }

    }

}
