package com.example.projetointegradorubs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    private fun showProgressBar() {
        setContentView(R.layout.progress_layout)
    }

    private fun hideProgressBar() {
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() // Inicialize a autenticação Firebase

        binding.buttonRegister.setOnClickListener {
            showProgressBar()
            val fullName = binding.editTextFullName.text.toString()
            val email = binding.editTextRegisterEmail.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val cpf = binding.editTextCpf.text.toString()
            val address = binding.editTextAddress.text.toString()
            val password = binding.editTextRegisterPassword.text.toString()

            if (fullName.isNotBlank() && email.isNotBlank() && phone.isNotBlank() &&
                cpf.isNotBlank() && address.isNotBlank() && password.isNotBlank()) {
                registerUser(fullName, email, phone, cpf, address, password)
            } else {
                hideProgressBar()
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun registerUser(
        fullName: String,
        email: String,
        phone: String,
        cpf: String,
        address: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro no Firebase Authentication bem-sucedido
                    val userId = auth.currentUser?.uid
                    val user = hashMapOf(
                        "fullName" to fullName,
                        "email" to email,
                        "phone" to phone,
                        "cpf" to cpf,
                        "address" to address
                    )
                    if (userId != null) {
                        db.collection("users").document(userId).set(user)
                            .addOnSuccessListener {
                                hideProgressBar()
                                Toast.makeText(this, "Registro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                hideProgressBar()
                                Toast.makeText(this, "Erro ao salvar dados: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    hideProgressBar()
                    // Se o registro falhar, mostre uma mensagem para o usuário
                    Toast.makeText(
                        this,
                        "Falha no registro: ${task.exception?.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        // Volta para a tela de login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Encerra a atividade atual
    }
}
