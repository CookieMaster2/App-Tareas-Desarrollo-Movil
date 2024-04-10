package com.example.tareadesarrollomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class Activity_Tarea7_Grid : AppCompatActivity() {
    private lateinit var RelativeButton : Button
    private lateinit var GridButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea7_grid)



        RelativeButton = findViewById(R.id.relativebutton)
        GridButton = findViewById(R.id.gridbutton)



        RelativeButton.setOnClickListener {
            Snackbar.make(findViewById(R.id.base_layout), "Ya estas aqui.", Snackbar.LENGTH_SHORT).show()
        }

        GridButton.setOnClickListener {
            val intent = Intent(this, Activity_Tarea7_Grid::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            finish()
        }
    }
}