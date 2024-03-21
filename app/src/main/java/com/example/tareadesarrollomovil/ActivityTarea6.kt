package com.example.tareadesarrollomovil

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest

var savedPicture: Uri? = null

class ActivityTarea6 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea6)
        val sendTextButton: Button = findViewById(R.id.sendTextButton)
        val editTextUserInput = findViewById<EditText>(R.id.inputText)
        val takePhotoButton: Button = findViewById(R.id.takePhotoButton)

        sendTextButton.setOnClickListener {
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
                startActivity(Intent.createChooser(sendIntent, "asdfasdfasdf"))
            }
        }

        takePhotoButton.setOnClickListener {

            //Si no se ha concecido el permiso para el uso de la cámara...
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                //Se hace la petición de los permisos
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSION_CAMERA
                )
            } else {

                //De lo contrario ejecuta la cámara
                dispatchTakePictureIntent()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("Tarea6Debug", "onPause")
    }

    //Función que despliega la cámara mediante un Intent para tomar una fotografía
    private fun dispatchTakePictureIntent() {

        //Llama un Intent implícito que ejecuta la acción de tomar una foto usando la cámara del dispositivo
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            //Inicia la aplicación de cámara usando el código para solicitar una imagen
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            //Si no hay una app de cámara por default, sale un toast
            Toast.makeText(this, "Camera app not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Si el código de la solicitud de imagen y el resultado del intent son adecuados
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //Obtiene los datos de la imagen del intent de la cámara
            val imageBitmap = data?.extras?.get("data")

            //Si se recibió un bitmap
            if (imageBitmap is Bitmap) {

                //Coloca la imagen o el thumbnail en el imageView correspondiente
                val imageView: ImageView = findViewById(R.id.imagenIncisoDos)
                imageView.setImageBitmap(imageBitmap)
            } else {
                // Si no recibe un bitmap o es nulo...
                Log.e(
                    "Tarea6Debug",
                    "Expected a Bitmap from camera intent, received something else."
                )
            }
        }
    }

    //Esta acción solamente es cuando se ejecuta la tarea por primera vez en el ciclo de
    // vida de la app
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //Revisa que el código para el permiso sea el correcto (es decir el de la cámara)
        when (requestCode) {
            REQUEST_PERMISSION_CAMERA -> {

                //Revisa que el arreglo de resultados de permisos NO esté vacío
                // Y si el priemr elemento es "Permission Granted", entonces ejecuta la función de cámara
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    dispatchTakePictureIntent()
                } else {

                    //De lo contario, muestra un toast
                    Toast.makeText(
                        this,
                        "Camera permission is needed to take pictures",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }

            //En caso de que nada coincida, llama de nuevo a la super clase por seguridad (xd)
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    //Códigos de permisos para captura de imagenes y uso de la cámara
    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private val REQUEST_PERMISSION_CAMERA = 2
    }

}
