package com.example.projetointegradorubs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.databinding.ActivityUserDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private fun showProgressBar() {
        setContentView(R.layout.progress_layout)
    }

    private fun hideProgressBar() {
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchUserDetails()
    }

    private fun fetchUserDetails() {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            // Mostrar progresso enquanto busca os dados
            showProgressBar()

            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Preencher os campos da interface com os dados do Firestore
                        binding.textViewFullName.text = document.getString("fullName")
                        binding.textViewEmail.text = document.getString("email")
                        binding.textViewPhone.text = document.getString("phone")
                        binding.textViewCpf.text = document.getString("cpf")
                        binding.textViewAddress.text = document.getString("address")
                    } else {
                        Toast.makeText(this, "Nenhum dado encontrado.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao buscar os dados: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener {
                    // Esconder progresso após completar a busca
                    hideProgressBar()
                }
        } else {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show()
        }
    }
}
