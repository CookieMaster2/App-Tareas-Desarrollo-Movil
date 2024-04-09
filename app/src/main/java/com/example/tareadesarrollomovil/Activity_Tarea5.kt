package com.example.tareadesarrollomovil

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Calendar

class Activity_Tarea5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea5)

        val btnShowDatePicker: Button = findViewById(R.id.btnShowDatePicker)
        val tvSelectedDate: TextView = findViewById(R.id.tvSelectedDate)

        btnShowDatePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, dayOfMonth ->
                // Display the selected date on the TextView
                val selectedDate = "${selectedMonth + 1}/$dayOfMonth/$selectedYear"
                tvSelectedDate.text = selectedDate
            }, year, month, day)

            datePickerDialog.show()
        }
    }
}