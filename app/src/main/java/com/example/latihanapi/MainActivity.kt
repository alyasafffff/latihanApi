package com.example.latihanapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanapi.adapter.CatatanAdapter
import com.example.latihanapi.databinding.ActivityMainBinding
import com.example.latihanapi.entities.Catatan
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var catatanAdapter: CatatanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupEvents()
    }

    override fun onResume() {
        super.onResume()
        getDataCatatan()
    }

    private fun setupRecyclerView() {
        // PERBAIKAN: Menambahkan listener interface di sini
        catatanAdapter = CatatanAdapter(mutableListOf(), object : CatatanAdapter.CatatanItemEvents {
            override fun onEdit(catatan: Catatan) {
                // Logika pindah ke halaman edit saat item diklik
                val intent = Intent(this@MainActivity, EditCatatanActivity::class.java)
                intent.putExtra("id_catatan", catatan.id)
                startActivity(intent)
            }
        })

        binding.rvCatatan.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = catatanAdapter
        }
    }

    private fun getDataCatatan() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.catatanRepository.getCatatan()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        catatanAdapter.updateDataset(data)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Gagal: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", e.toString())
            }
        }
    }

    private fun setupEvents() {
        binding.btnNavigate.setOnClickListener {
            val intent = Intent(this, CreateCatatan::class.java)
            startActivity(intent)
        }
    }
}