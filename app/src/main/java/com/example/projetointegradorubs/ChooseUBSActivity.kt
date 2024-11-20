package com.example.projetointegradorubs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetointegradorubs.databinding.ActivityChooseUbsBinding


class ChooseUBSActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseUbsBinding
    private lateinit var ubsList: List<UBS>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChooseUbsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.recyclerViewUBS.layoutManager = LinearLayoutManager(this)
        ubsList = listOf(
            UBS("UBS Jardim Marajoara", "Rua Amazonas, 419 - Jardim Marajoara"),
            UBS("UBS Campo Limpo", "Rua Visconde de Inhaúma, 434 - Campo Limpo"),
            UBS("UBS Vila Progredior", "Rua General Jardim, 1090 - Vila Progredior"),
            UBS("UBS Butantã", "Avenida Engenheiro Heitor Antônio Eiras García, 1.560 - Butantã")
        )

        val adapter = UBSAdapter(ubsList) { ubs ->
            // Ação ao clicar em uma UBS
            Toast.makeText(this, "Selecionou: ${ubs.name}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,CalendarActivity::class.java)
            startActivity(intent)
        }

        binding.recyclerViewUBS.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}