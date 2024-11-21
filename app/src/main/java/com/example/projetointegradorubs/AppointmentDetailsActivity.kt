package com.example.projetointegradorubs

import Appointment
import AppointmentAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetointegradorubs.databinding.ActivityAppointmentBinding
import com.example.projetointegradorubs.databinding.ActivityAppointmentDetailsBinding
import com.example.projetointegradorubs.databinding.ActivityScheduleBinding

class AppointmentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAppointmentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appointments = listOf(
            Appointment("UBS Central", "Consulta Médica", "25/11/2024", "14:00"),
            Appointment("UBS Bairro X", "Vacinação", "27/11/2024", "09:00"),
            Appointment("UBS Zona Sul", "Consulta Odontológica", "30/11/2024", "10:30")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAppointments)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AppointmentAdapter(appointments)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}