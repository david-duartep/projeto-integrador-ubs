package com.example.projetointegradorubs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            binding.progressBarLogin.visibility = android.view.View.VISIBLE
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }

        binding.buttonRegister.setOnClickListener {
            binding.progressBarLogin.visibility = android.view.View.VISIBLE
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
        binding.progressBarLogin.visibility = View.GONE
    }
}