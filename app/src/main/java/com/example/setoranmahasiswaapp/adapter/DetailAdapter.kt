package com.example.setoranmahasiswaapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.setoranmahasiswaapp.databinding.ItemDetailBinding
import com.example.setoranmahasiswaapp.model.DetailItem

class DetailAdapter : ListAdapter<DetailItem, DetailAdapter.VH>(diff) {

  companion object {
    private val diff = object: DiffUtil.ItemCallback<DetailItem>() {
      override fun areItemsTheSame(o: DetailItem, n: DetailItem) = o.id == n.id
      override fun areContentsTheSame(o: DetailItem, n: DetailItem) = o == n
    }
  }

  inner class VH(val b: ItemDetailBinding) : RecyclerView.ViewHolder(b.root) {
    fun bind(d: DetailItem) {
      b.tvNama.text     = d.nama
      b.tvLabel.text    = d.label
      b.tvSetor.text    = if (d.sudahSetor) "✓" else "✗"
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    VH(ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: VH, pos: Int) =
    holder.bind(getItem(pos))
}
