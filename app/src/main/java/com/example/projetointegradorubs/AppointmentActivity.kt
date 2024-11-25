package com.example.projetointegradorubs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.databinding.ActivityAppointmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class AppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var selectedDate: String? = null
    private lateinit var selectedUBS: UBS // UBS selecionada
    private var selectedSpecialty: String? = null // Especialidade selecionada

    private fun showProgressBar() {
        setContentView(R.layout.progress_layout)
    }

    private fun hideProgressBar() {
        setContentView(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Recebe a UBS selecionada e a especialidade da Intent
        selectedUBS = intent.getSerializableExtra("selectedUBS") as UBS
        selectedDate = intent.getStringExtra("SELECTED_DATE")
        selectedSpecialty = intent.getStringExtra("selectedSpecialty")

        binding.textViewSelectedDate.text = "Data Selecionada: $selectedDate"

        val availableTimes = arrayOf("08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00", "17:00")
        val adapter = ArrayAdapter(this, R.layout.spinner_selected_item, availableTimes)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.spinnerTime.adapter = adapter

        binding.buttonConfirm.setOnClickListener {
            showProgressBar()
            val selectedTime = binding.spinnerTime.selectedItem.toString()

            if (selectedTime.isNotEmpty()) {
                saveAppointment(selectedDate, selectedTime, selectedUBS, selectedSpecialty)
            } else {
                Toast.makeText(this, "Por favor, selecione o horário da consulta", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun saveAppointment(date: String?, time: String, ubs: UBS, specialty: String?) {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            val appointment = hashMapOf(
                "userId" to userId,
                "date" to date,
                "time" to time,
                "specialty" to specialty, // Adiciona a especialidade
                "ubs" to hashMapOf(
                    "name" to ubs.name,
                    "address" to ubs.address
                )
            )

            db.collection("appointments").add(appointment)
                .addOnSuccessListener {
                    Toast.makeText(this, "Consulta agendada com sucesso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao agendar: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        hideProgressBar()
    }
}