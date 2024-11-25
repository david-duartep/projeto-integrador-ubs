package com.example.projetointegradorubs

import Appointment
import AppointmentAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetointegradorubs.databinding.ActivityAppointmentDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentDetailsBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun showProgressBar() {
        setContentView(R.layout.progress_layout)
    }

    private fun hideProgressBar() {
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usando ViewBinding para vincular o layout
        binding = ActivityAppointmentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNewAppointment.setOnClickListener {
            showProgressBar()
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }

        // Configurando o RecyclerView
        binding.recyclerViewAppointments.layoutManager = LinearLayoutManager(this)
        fetchAppointments()
    }

    private fun fetchAppointments() {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            db.collection("appointments")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { documents ->
                    val appointments = documents.map { document ->
                        Appointment(
                            type = document.getString("specialty") ?: "Consulta",
                            ubsName = document.getString("ubs.name") ?: "UBS Desconhecida",
                            date = document.getString("date") ?: "Data Não Informada",
                            time = document.getString("time") ?: "Horário Não Informado"
                        )
                    }

                    // Configurando o Adapter
                    binding.recyclerViewAppointments.adapter = AppointmentAdapter(
                        appointments,
                        onEdit = { appointment ->
                            Toast.makeText(this, "Editar: ${appointment.type}", Toast.LENGTH_SHORT).show()
                            // Lógica para editar
                        },
                        onDelete = { appointment ->
                            Toast.makeText(this, "Excluir: ${appointment.type}", Toast.LENGTH_SHORT).show()
                            deleteAppointment()
                        }
                    )
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao buscar agendamentos: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteAppointment() {
        // Implemente a lógica para exclusive um agendamento do Firebase
        Toast.makeText(this, "Excluir agendamento ainda não implementado.", Toast.LENGTH_SHORT).show()
    }
    override fun onResume() {
        super.onResume()
        hideProgressBar()
    }
}