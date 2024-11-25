package com.example.projetointegradorubs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() // Inicialize a autenticação Firebase

        binding.buttonLogin.setOnClickListener {
            binding.progressBarLogin.visibility = View.VISIBLE
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            loginUser(email, password)
        }

        binding.buttonRegister.setOnClickListener {
            binding.progressBarLogin.visibility = View.VISIBLE
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            registerUser(email, password)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.progressBarLogin.visibility = View.GONE
                if (task.isSuccessful) {
                    // Registro bem-sucedido, redirecionar para MenuActivity
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Se o registro falhar, mostre uma mensagem para o usuário.
                    Toast.makeText(baseContext, "Falha no registro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.progressBarLogin.visibility = View.GONE
                if (task.isSuccessful) {
                    // Login bem-sucedido, redirecionar para MenuActivity
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Se o login falhar, mostre uma mensagem para o usuário.
                    Toast.makeText(baseContext, "Falha no login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onResume() {
        super.onResume()
        // Parar a animação e esconder a ProgressBar quando voltar para esta Activity
        binding.progressBarLogin.visibility = View.GONE
    }
}
