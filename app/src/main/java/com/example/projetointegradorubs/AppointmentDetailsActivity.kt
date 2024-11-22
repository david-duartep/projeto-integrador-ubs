package com.example.projetointegradorubs

import Appointment
import AppointmentAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetointegradorubs.databinding.ActivityAppointmentDetailsBinding

class AppointmentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentDetailsBinding

    private fun showProgressBar() {
        setContentView(R.layout.progress_layout)
    }

    private fun hideProgressBar() {
        setContentView(binding.root)
    }

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

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_Appointments)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AppointmentAdapter(appointments)

        binding.buttonNewAppointment.setOnClickListener {
            showProgressBar()
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        // Parar a animação e esconder a ProgressBar quando voltar para esta Activity
        hideProgressBar()
    }
}