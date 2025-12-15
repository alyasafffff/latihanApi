package com.example.latihanapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanapi.databinding.ListCatatanBinding
import com.example.latihanapi.entities.Catatan

class CatatanAdapter(
    private val dataset: MutableList<Catatan>,
    private val events: CatatanItemEvents
): RecyclerView.Adapter<CatatanAdapter.CatatanViewHolder>() {

    interface CatatanItemEvents{
        fun onEdit(catatan: Catatan)
    }

    inner class CatatanViewHolder(val binding: ListCatatanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setDataKeUI(data: Catatan) {
            binding.tvJudul.text = data.judul
            binding.tvIsi.text = data.isi

            // --- TAMBAHAN PENTING: Aksi Klik ---
            itemView.setOnClickListener {
                events.onEdit(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanViewHolder {
        val binding = ListCatatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatatanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatatanViewHolder, position: Int) {
        val dataSekarang = dataset[position]
        holder.setDataKeUI(dataSekarang)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateDataset(dataBaru: List<Catatan>) {
        dataset.clear()
        dataset.addAll(dataBaru)
        notifyDataSetChanged()
    }
}