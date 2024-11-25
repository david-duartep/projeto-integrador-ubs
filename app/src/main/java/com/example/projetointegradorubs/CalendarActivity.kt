package com.example.projetointegradorubs

import AppointmentActivity
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.databinding.ActivityCalendarBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private var selectedDate: String? = null  // Variável para armazenar a data selecionada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Inicialmente o botão não tem cor e está desativado
        binding.buttonSchedule.isEnabled = false
        binding.buttonSchedule.alpha = 0.5f
        binding.buttonSchedule.setBackgroundColor(resources.getColor(R.color.blue))
        binding.buttonSchedule.setTextColor(resources.getColor(android.R.color.white))

        // Configura a data mínima para a seleção (data atual)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 2) // Adiciona dois dias à data atual
        val minDate = calendar.timeInMillis
        binding.calendarView.minDate = minDate // Desabilita datas antes de dois dias

        // Configura a data máxima para a seleção (20 dias após a data atual)
        calendar.add(Calendar.DAY_OF_YEAR, 20)
        val maxDate = calendar.timeInMillis
        binding.calendarView.maxDate = maxDate  // Limita para 20 dias após a data atual

        // Configura o listener para a seleção da data
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            // Verifica se a data selecionada é sábado ou domingo
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            // Se for sábado (7) ou domingo (1), desabilita a seleção
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                // Exibe um aviso para o usuário
                Toast.makeText(this, "Fins de semana não são permitidos", Toast.LENGTH_SHORT).show()

                // Desativa o botão e reseta sua cor para indicar que está desativado
                binding.buttonSchedule.isEnabled = false
                binding.buttonSchedule.alpha = 0.5f
                binding.buttonSchedule.setBackgroundColor(resources.getColor(R.color.blue))
                binding.buttonSchedule.setTextColor(resources.getColor(R.color.white))

                return@setOnDateChangeListener
            }

            // Formatação da data selecionada
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            selectedDate = dateFormat.format(calendar.time)

            // Ativa o botão quando uma data é selecionada e não for fim de semana
            binding.buttonSchedule.isEnabled = true
            binding.buttonSchedule.alpha = 1f
            binding.buttonSchedule.setBackgroundColor(resources.getColor(R.color.blue)) // fundo azul
            binding.buttonSchedule.setTextColor(resources.getColor(android.R.color.white)) // texto branco
        }

        // Configura o clique no botão para agendar a consulta
        binding.buttonSchedule.setOnClickListener {
            if (selectedDate != null) {
                // Passa a data selecionada para a próxima Activity
                val intent = Intent(this, AppointmentActivity::class.java)
                intent.putExtra("SELECTED_DATE", selectedDate) // Passa a data
                startActivity(intent)
            }
        }

        // Ajuste da tela para dispositivos com bordas
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
