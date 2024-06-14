package com.example.skinsync.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skinsync.data.listproduct.DataListProduct
import com.example.skinsync.databinding.ListProductUsersBinding

class ListProductAdapter(private val listProduct: ArrayList<DataListProduct>) : RecyclerView.Adapter<ListProductAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListProductUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (type, name, photo) = listProduct[position]
        holder.binding.imageProduct.setImageResource(photo)
        holder.binding.tvTypeProduct.text = type
        holder.binding.tvNameProduct.text = name
    }

    class ListViewHolder(var binding: ListProductUsersBinding) : RecyclerView.ViewHolder(binding.root)
}