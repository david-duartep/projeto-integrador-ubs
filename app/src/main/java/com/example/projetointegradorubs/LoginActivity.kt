package com.example.projetointegradorubs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private fun showProgressBar() {
        setContentView(R.layout.progress_layout)
    }

    private fun hideProgressBar() {
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() // Inicialize a autenticação Firebase

        // Configurar comportamento ao clicar nos botões
        binding.buttonLogin.setOnClickListener {
            showProgressBar()
            val email = binding.editTextLoginEmail.text.toString()
            val password = binding.editTextLoginPassword.text.toString()
            if (email.isNotBlank() && password.isNotBlank()) {
                loginUser(email, password)
            } else {
                hideProgressBar()
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonRegister.setOnClickListener {
            showProgressBar()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Listener para mover o cursor para o início ao perder o foco
        binding.editTextLoginEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextLoginEmail.setSelection(0)
            }
        }

        binding.editTextLoginPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.editTextLoginPassword.setSelection(0)
            }
        }

        // Listener para mover o cursor para o início ao pressionar "Enter"
        binding.editTextLoginEmail.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                binding.editTextLoginEmail.setSelection(0)
            }
            false
        }

        binding.editTextLoginPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                binding.editTextLoginPassword.setSelection(0)
            }
            false
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                hideProgressBar()
                if (task.isSuccessful) {
                    // Login bem-sucedido, redirecionar para MenuActivity
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Se o login falhar, verifique o tipo de erro
                    when (val exception = task.exception) {
                        is FirebaseAuthInvalidUserException -> {
                            Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show()
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Toast.makeText(this, "Dados incorretos", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(
                                this,
                                "Erro ao fazer login: ${exception?.localizedMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        // Parar a animação e esconder a ProgressBar quando voltar para esta Activity
        hideProgressBar()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        // Volta para a tela inicial (MainActivity)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Encerra a atividade atual
    }
}