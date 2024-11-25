package com.example.projetointegradorubs

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.databinding.ActivityScheduleBinding

class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    private var selectedSpecialty: String? = null // Variável para armazenar a especialidade selecionada

    private fun showProgressBar() {
        setContentView(R.layout.progress_layout)
    }

    private fun hideProgressBar() {
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_options, // O array criado no strings.xml
            R.layout.spinner_selected_item // Layout para o item selecionado
        )

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.spinnerSpecialty.adapter = adapter

        // Listener para capturar a especialidade selecionada
        binding.spinnerSpecialty.onItemSelectedListener = object :
            android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                selectedSpecialty = parent?.getItemAtPosition(position).toString()
                Toast.makeText(
                    this@ScheduleActivity,
                    "Selecionado: $selectedSpecialty",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                // Nenhuma ação necessária aqui
            }
        }

        binding.buttonSchedule.setOnClickListener {
            if (selectedSpecialty.isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "Por favor, selecione uma especialidade.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                showProgressBar()
                // Passando a especialidade selecionada para a próxima Activity
                val intent = Intent(this, ChooseUBSActivity::class.java)
                intent.putExtra("selectedSpecialty", selectedSpecialty)
                startActivity(intent)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        hideProgressBar() // Parar a animação e esconder a ProgressBar quando voltar para esta Activity
    }
}