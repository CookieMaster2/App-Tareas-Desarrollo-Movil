package com.example.tareadesarrollomovil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class ActivityTarea6 : AppCompatActivity() {
    lateinit var inputText: EditText
    private lateinit var currentPhotoPath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea6)
        val sendTextButton: Button = findViewById(R.id.sendTextButton)
        val editTextUserInput = findViewById<EditText>(R.id.inputText)
        val takePhotoButton: Button = findViewById(R.id.takePhotoButton)

        sendTextButton.setOnClickListener{
            val userInput = editTextUserInput.text.toString()
            val sendIntent = Intent()
            sendIntent.setAction(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, userInput)
            sendIntent.setType("text/plain")
            // Check if there's an app that can handle this intent
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(sendIntent, "Choose an app"))
            }
        }
        val data: Uri? = intent?.data
        val phoneNumber = data?.schemeSpecificPart

        takePhotoButton.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<String>, grantResults:
//        IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        val REQUEST_CODE_PERMISSIONS = 1
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (allPermissionsGranted()) {
//                startCamera()
//            } else {
//                Toast.makeText(this,
//                    "Permissions not granted by the user.",
//                    Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        }
//    }
}
