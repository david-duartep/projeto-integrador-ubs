package com.example.projetointegradorubs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetointegradorubs.databinding.ActivityMenuBinding
import org.jetbrains.annotations.Async.Schedule

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

    private fun showProgressBar() {
        setContentView(R.layout.progress_layout)
    }

    private fun hideProgressBar() {
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.buttonExit.setOnClickListener {
            showProgressBar()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.buttonNewAppointment.setOnClickListener {
            showProgressBar()
            val intent = Intent(this,ScheduleActivity::class.java)
            startActivity(intent)
        }

        binding.buttonScheduled.setOnClickListener {
            showProgressBar()
            val intent = Intent(this,AppointmentDetailsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonHistory.setOnClickListener {
            showProgressBar()
            val intent = Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }

        binding.buttonUserProfile.setOnClickListener {
            showProgressBar()
            val intent = Intent(this,UserDetailsActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onResume() {
        super.onResume()
        hideProgressBar()
    }
}
