package com.example.tareadesarrollomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var tarea_4: Button
    private lateinit var tarea_5: Button
    private lateinit var tarea_6: Button
    private lateinit var tarea_7: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tarea_4 = findViewById(R.id.button_tarea4)
        tarea_5 = findViewById(R.id.button_tarea5)
        tarea_6 = findViewById(R.id.button_tarea6)
        tarea_7 = findViewById(R.id.button_tarea7)

        tarea_4.setOnClickListener{
            val intent = Intent(this, Activity_Tarea4::class.java)
            startActivity(intent)
        }

        tarea_5.setOnClickListener{
            val intent = Intent(this, Activity_Tarea5::class.java)
            startActivity(intent)
        }

        tarea_6.setOnClickListener{
            val intent = Intent(this, Activity_Tarea6::class.java)
            startActivity(intent)
        }

        tarea_7.setOnClickListener{
            val intent = Intent(this, Activity_Tarea7::class.java)
            startActivity(intent)
        }
    }
}