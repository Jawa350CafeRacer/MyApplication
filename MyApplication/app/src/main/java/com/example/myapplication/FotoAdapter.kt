package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding.bind
import com.example.myapplication.databinding.FotoItemBinding

class FotoAdapter: RecyclerView.Adapter<FotoAdapter.FotoHolder>() {
    val fotolist = ArrayList<Foto>()

    class FotoHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = FotoItemBinding.bind(item)

        fun bind(foto: Foto) = with(binding) {
            binding.im.setImageResource(foto.imageId)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.foto_item, parent, false)
        return FotoHolder(view)
    }

    override fun onBindViewHolder(holder: FotoHolder, position: Int) {
        holder.bind(fotolist[position])
    }

    override fun getItemCount(): Int {
       return fotolist.size
    }

    fun addFoto(foto: Foto){
        fotolist.add(foto)
        notifyDataSetChanged()
    }
}
