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
import com.example.latihanapi.databinding.ActivityEditCatatanBinding
import com.example.latihanapi.entities.Catatan
import kotlinx.coroutines.launch

class EditCatatanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCatatanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // --- PERBAIKAN 1: Inisialisasi Binding yang Benar ---
        binding = ActivityEditCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // JANGAN ADA setContentView(R.layout.activity_edit_catatan) LAGI DI SINI!
        // Itu yang membuat layar kosong/data tidak muncul.

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEvents()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun setupEvents(){
        binding.tombolEdit.setOnClickListener{
            val id = intent.getIntExtra("id_catatan", 0)
            val judul = binding.inputJudul.text.toString()
            val isi = binding.inputIsi.text.toString()

            if (isi.isEmpty() || judul.isEmpty()){
                displayMessage("Judul dan isi catatan harus diisi")
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    // Pastikan constructor Catatan sesuai dengan Data Class kamu (tambahkan user_id jika perlu)
                    val catatan = Catatan(id, judul, isi, 1)
                    val response = RetrofitClient.catatanRepository.editCatatan(id, catatan)

                    if (response.isSuccessful){
                        displayMessage("Catatan berhasil diubah")
                        switchPage(MainActivity::class.java)
                    } else {
                        displayMessage("Gagal ubah: ${response.message()}")
                    }
                } catch (e: Exception) {
                    displayMessage("Error Jaringan: ${e.message}")
                    Log.e("EditError", e.toString())
                }
            }
        }
    }

    fun loadData(){
        val id = intent.getIntExtra("id_catatan", 0)

        // Cek apakah ID berhasil diterima dari MainActivity
        if(id == 0){
            displayMessage("Error: ID Catatan bernilai 0 atau tidak ditemukan")
            return
        }

        lifecycleScope.launch {
            try {
                // Panggil API
                val response = RetrofitClient.catatanRepository.getCatatan(id)

                if (response.isSuccessful){
                    val catatan = response.body()
                    if (catatan != null) {
                        // --- PERBAIKAN: Masukkan data ke Text Field ---
                        binding.inputJudul.setText(catatan.judul)
                        binding.inputIsi.setText(catatan.isi)
                    } else {
                        displayMessage("Data kosong dari server")
                    }
                } else {
                    displayMessage("Gagal server: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                // --- PERBAIKAN 2: Tampilkan pesan error ASLI ---
                // Ini akan memberitahu kita apakah errornya karena Jaringan atau JSON
                displayMessage("Error Load: ${e.localizedMessage}")
                Log.e("LoadError", "Error loading data", e)
            }
        }
    }

    fun displayMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun switchPage(destination: Class<*>){
        val intent = Intent(this, destination)
        startActivity(intent)
        finish()
    }
}