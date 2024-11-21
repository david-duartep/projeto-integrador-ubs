package com.example.projetointegradorubs

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.databinding.ActivityAppointmentBinding

class AppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentBinding
    private var selectedDate: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Pega a data da Intent
        selectedDate = intent.getStringExtra("SELECTED_DATE")
        binding.textViewSelectedDate.text = "Data Selecionada: $selectedDate"

        // Lista de horários pré-definidos
        val availableTimes = arrayOf("08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00", "17:00")

        // Cria o ArrayAdapter com os horários disponíveis
        val adapter = ArrayAdapter(this, R.layout.spinner_selected_item, availableTimes)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        // Configura o Spinner com o Adapter
        binding.spinnerTime.adapter = adapter

        // Configura o botão de confirmação
        binding.buttonConfirm.setOnClickListener {
            // Pega o horário selecionado
            val selectedTime = binding.spinnerTime.selectedItem.toString()
            // Verifica se o usuário selecionou o horário
            if (selectedTime.isNotEmpty()) {
                // Mostra um Toast confirmando o agendamento
                Toast.makeText(this, "Consulta agendada para $selectedDate às $selectedTime", Toast.LENGTH_SHORT).show()
                // Cria uma intent para voltar à MenuActivity
                val intent = Intent(this, MenuActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // Garante que as atividades anteriores são limpas
                startActivity(intent) // Abre a MenuActivity
                finish() // Finaliza a AppointmentActivity
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
}