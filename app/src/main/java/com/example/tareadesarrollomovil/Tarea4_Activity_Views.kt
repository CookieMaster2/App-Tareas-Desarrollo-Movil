package com.example.tarea4

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Strings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import com.google.android.material.snackbar.Snackbar

//
//Tarea #4: Controles básicos
//
//Este trabajo de programación es individual.
//
//Se debe realizar una aplicación demostrativa básica del funcionamiento de los siguientes controles: ToggleButton, Switch, ImageView, ImageButton, ScrollView y ListView. La aplicación debe incluir: (a) declaración en el layout XML, (b) declaracion e inicialización de las variables referencia en Kotlin, (c) generación de los manejadores de eventos comunes (listeners) y (d) demostración de funcionalidad básica.
//


class Tarea4_Activity_Views : AppCompatActivity() {

    lateinit var listView:ListView
    lateinit var scrollView:NestedScrollView
    lateinit var HscrollView:HorizontalScrollView

    private lateinit var toggleButtonsView : Button
    private lateinit var imagesView : Button
    private lateinit var viewView : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea4_views)

        toggleButtonsView =  findViewById(R.id.TgBtnBtn)
        imagesView =  findViewById(R.id.ImgBtn)
        viewView =  findViewById(R.id.VwBtn)

        scrollView = findViewById(R.id.scrollView)
        HscrollView = findViewById(R.id.HscrollView)


        listView = findViewById<ListView>(R.id.listView)

        val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")

        val arrayAdapter : ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, items
        )

        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this,  "Item seleccionado: "+ items[i], Toast.LENGTH_SHORT).show()
        }

        scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Toast.makeText(this,"Scrolleado", Toast.LENGTH_SHORT).show()
        }
        HscrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Toast.makeText(this,"Scrolleado", Toast.LENGTH_SHORT).show()
        }


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
            Toast.makeText(this,"Ya estas aqui.", Toast.LENGTH_SHORT).show()


        }
    }
}