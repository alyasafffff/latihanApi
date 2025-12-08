package com.example.latihanapi

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.lifecycleScope
import com.example.latihanapi.databinding.ActivityCreateCatatanBinding
import com.example.latihanapi.entities.Catatan
import kotlinx.coroutines.launch


class CreateCatatan : AppCompatActivity() {
    private lateinit var binding : ActivityCreateCatatanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCreateCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root) // <--- CUKUP INI SAJA

        // HAPUS BARIS INI: setContentView(R.layout.activity_create_catatan)
        // Baris di atas membuat tombol "Simpan" tidak bisa diklik.

        // Ganti findViewById(R.id.main) menjadi binding.main
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEvents()
    }

    fun setupEvents(){
        binding.tombolSimpan.setOnClickListener{
            val judul = binding.inputJudul.text.toString()
            val isi = binding.inputIsi.text.toString()

            if (judul.isEmpty()||isi.isEmpty()){
                displayMessage("judul dan isi catatan harus di isi")
                return@setOnClickListener
            }

            val payload = Catatan(
                judul = judul,
                isi  = isi,
                id = null,
                user_id = 1
            )

            lifecycleScope.launch {
                val response = RetrofitClient.catatanRepository.createCatatan(payload)
                if (response.isSuccessful){
                    displayMessage("Catatan Berhasil Di tambahkan")

                    val intent = Intent(this@CreateCatatan, MainActivity::class.java)
                    startActivity(intent)

                    finish()
                }else{
                    displayMessage("Gagal : ${response}")
                }
            }
        }
    }

    fun displayMessage(messsage : String){
        Toast.makeText(this, messsage, Toast.LENGTH_SHORT).show()
    }
}