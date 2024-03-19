package com.example.tarea4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Tarea4_Activity : AppCompatActivity() {

    private lateinit var toggleButtonsView : Button
    private lateinit var imagesView : Button
    private lateinit var viewView : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea4)



        toggleButtonsView =  findViewById(R.id.TgBtnBtn)
        imagesView =  findViewById(R.id.ImgBtn)
        viewView =  findViewById(R.id.VwBtn)


        toggleButtonsView.setOnClickListener {
            val intent = Intent(this, Tarea4_Activity_ToggleButton::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            finish()
        }

        imagesView.setOnClickListener {
            val intent = Intent(this, Tarea4_Activity_Images::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            finish()
        }

        viewView.setOnClickListener {
            val intent = Intent(this, Tarea4_Activity_Views::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            finish()
        }

    }







}