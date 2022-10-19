package com.example.myapplication

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FotoItemBinding

class FotoAdapter(val listener: Listener): RecyclerView.Adapter<FotoAdapter.FotoHolder>() {
    var fotolist = ArrayList<Uri>()


    class FotoHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = FotoItemBinding.bind(item)

        fun bind(uri: Uri, listener: Listener) = with(binding) {
           im.setImageURI(uri)
            itemView.setOnClickListener {
                listener.onclick(uri)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.foto_item, parent, false)
        return FotoHolder(view)
    }

    override fun onBindViewHolder(holder: FotoHolder, position: Int) {
        holder.bind(fotolist.get(position), listener)
    }

    override fun getItemCount(): Int {
       return fotolist.size
    }

    interface Listener{
        fun onclick(uri: Uri)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(uri: Uri?) {
        fotolist.remove(uri)
        notifyDataSetChanged()
    }
}
