package com.example.latihanapi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.latihanapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Siapkan Binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 2. Pasang tampilan dari binding (JANGAN panggil setContentView lagi di bawahnya!)
        setContentView(binding.root)

        // HAPUS BARIS INI: setContentView(R.layout.activity_main)
        // Baris di atas adalah penyebab tombol tidak bisa diklik.

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 3. PENTING: Panggil fungsi ini agar tombol aktif
        setupEvents()
    }

    private fun setupEvents(){
        binding.btnNavigate.setOnClickListener{
            // Logika pindah halaman
            val intent = Intent(this, CreateCatatan::class.java)
            startActivity(intent)
        }
    }
}