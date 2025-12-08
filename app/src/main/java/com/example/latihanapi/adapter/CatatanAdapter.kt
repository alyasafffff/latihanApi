package com.example.latihanapi.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanapi.entities.Catatan

class CatatanAdapter(private val dataset: MutableList<Catatan>): RecyclerView.Adapter<CatatanAdapter.CatatanViewHolder>() {

    inner class CatatanViewHolder(val view : ItemCatatanBinding):RecyclerView.ViewHolder(view.root){
        fun setDataKeUI(data: Catatan){

        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatatanAdapter.CatatanViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CatatanAdapter.CatatanViewHolder, position: Int) {
        val dataSekarang = dataset(position)
        holder.setDataKeUI(dataSekarang)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateDataset(dataBaru : List<Catatan>){
        dataset.clear()
        dataset.addAll(dataBaru)
        notifyDataSetChanged()
    }

}