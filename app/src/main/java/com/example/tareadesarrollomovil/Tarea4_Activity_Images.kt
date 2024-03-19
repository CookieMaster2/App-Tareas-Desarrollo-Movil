package com.example.tareadesarrollomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar

class Tarea4_Activity_Images : AppCompatActivity() {
    private lateinit var imageView : ImageView
    var  imageViewClicked : Boolean = false


    private lateinit var imageButton : ImageButton
    var  imageButtonClicked : Boolean = false

    private lateinit var toggleButtonsView : Button
    private lateinit var imagesView : Button
    private lateinit var viewView : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea4_images)

        imageView = findViewById(R.id.imageView)
        imageButton = findViewById(R.id.imageButton)

        toggleButtonsView =  findViewById(R.id.TgBtnBtn)
        imagesView =  findViewById(R.id.ImgBtn)
        viewView =  findViewById(R.id.VwBtn)

        imageView.setOnClickListener {
            if(imageViewClicked){
                imageView.setImageResource(R.drawable.ic_launcher_background)
                imageViewClicked=false

            }
            else{
                imageView.setImageResource(R.drawable.ic_launcher_foreground)
                imageViewClicked=true
            }
        }

        imageButton.setOnClickListener {
            if(imageButtonClicked){
                imageButton.setImageResource(R.drawable.ic_launcher_background)
                imageButtonClicked=false

            }
            else{
                imageButton.setImageResource(R.drawable.ic_launcher_foreground)
                imageButtonClicked=true
            }
        }

        toggleButtonsView.setOnClickListener {
            val intent = Intent(this, Tarea4_Activity_ToggleButton::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            finish()
        }

        imagesView.setOnClickListener {
            Snackbar.make(findViewById(R.id.base_layout), "Ya estas aqui.", Snackbar.LENGTH_SHORT).show()
        }

        viewView.setOnClickListener {
            val intent = Intent(this, Tarea4_Activity_Views::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            finish()

        }

    }
}