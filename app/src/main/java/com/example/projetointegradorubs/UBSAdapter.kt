package com.example.projetointegradorubs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetointegradorubs.databinding.ItemUbsBinding

data class UBS(
    val name: String,
    val address: String
)

class UBSAdapter(private val ubsList: List<UBS>, private val listener: (UBS) -> Unit) :
    RecyclerView.Adapter<UBSAdapter.UBSViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UBSViewHolder {
        // Usando ViewBinding para inflar o layout
        val binding = ItemUbsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UBSViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UBSViewHolder, position: Int) {
        val ubs = ubsList[position]
        holder.bind(ubs, listener)
    }

    override fun getItemCount(): Int = ubsList.size

    // ViewHolder com ViewBinding
    class UBSViewHolder(private val binding: ItemUbsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ubs: UBS, listener: (UBS) -> Unit) {
            binding.tvUBSName.text = ubs.name
            binding.tvUBSAddress.text = ubs.address

            binding.root.setOnClickListener {
                listener(ubs)
            }
        }
    }
}
