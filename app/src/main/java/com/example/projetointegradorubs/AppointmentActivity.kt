import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.MenuActivity
import com.example.projetointegradorubs.R // Certifique-se de importar o R correto
import com.example.projetointegradorubs.databinding.ActivityAppointmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentBinding
    private var selectedDate: String? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Inicialize o Firebase Database
        database = FirebaseDatabase.getInstance().reference

        // Pega a data da Intent
        selectedDate = intent.getStringExtra("SELECTED_DATE")
        binding.textViewSelectedDate.text = "Data Selecionada: $selectedDate"

        // Lista de horários pré-definidos
        val availableTimes = arrayOf("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00")

        // Cria o ArrayAdapter com os horários disponíveis
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, availableTimes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Configura o Spinner com o Adapter
        binding.spinnerTime.adapter = adapter

        // Configura o botão de confirmação
        binding.buttonConfirm.setOnClickListener {
            // Pega o horário selecionado
            val selectedTime = binding.spinnerTime.selectedItem.toString()
            // Verifica se o usuário selecionou o horário
            if (selectedTime.isNotEmpty()) {
                // Salva os detalhes do agendamento no Firebase
                saveAppointmentToFirebase(selectedDate, selectedTime)

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

        // Aplicação dos WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun saveAppointmentToFirebase(selectedDate: String?, selectedTime: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val appointmentId = database.child("appointments").push().key
            val appointment = Appointment(userId, selectedDate, selectedTime)

            if (appointmentId != null) {
                database.child("appointments").child(appointmentId).setValue(appointment)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Dados do agendamento salvos com sucesso
                        } else {
                            // Falha ao salvar dados do agendamento
                            Toast.makeText(this, "Falha ao salvar o agendamento.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    data class Appointment(
        val userId: String,
        val date: String?,
        val time: String
    )
}
