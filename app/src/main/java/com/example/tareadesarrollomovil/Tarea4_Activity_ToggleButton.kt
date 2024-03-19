package com.example.tareadesarrollomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial

class Tarea4_Activity_ToggleButton : AppCompatActivity() {

    private lateinit var switchMaterial : SwitchMaterial
    private lateinit var switchMaterialText : TextView


    private lateinit var switchCompat : SwitchCompat
    private lateinit var switchCompatlText : TextView


    private lateinit var toggleButton : ToggleButton
    private lateinit var toggleButtonText : TextView


    private lateinit var toggleButtonsView : Button
    private lateinit var imagesView : Button
    private lateinit var viewView : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea4_togglebutton)

        switchMaterial = findViewById(R.id.material_switch)
        switchMaterialText = findViewById(R.id.SwitchMaterialText)


        switchCompat = findViewById(R.id.switchcompat)
        switchCompatlText = findViewById(R.id.SwitchCompatText)


        toggleButton = findViewById(R.id.toggle)
        toggleButtonText = findViewById(R.id.ToggleButtonText)


        toggleButtonsView =  findViewById(R.id.TgBtnBtn)
        imagesView =  findViewById(R.id.ImgBtn)
        viewView =  findViewById(R.id.VwBtn)



        switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchMaterialText.visibility = View.VISIBLE
                //button.setBackgroundColor(Color.parseColor("#ffffff"))
            } else {
                switchMaterialText.visibility = View.INVISIBLE
            }
        }

        switchCompat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchCompatlText.visibility = View.VISIBLE
            } else {
                switchCompatlText.visibility = View.INVISIBLE
            }
        }

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                toggleButtonText.visibility = View.VISIBLE
            } else {
                toggleButtonText.visibility = View.INVISIBLE
            }
        }

        toggleButtonsView.setOnClickListener {
            Snackbar.make(findViewById(R.id.base_layout), "Ya estas aqui.", Snackbar.LENGTH_SHORT).show()
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