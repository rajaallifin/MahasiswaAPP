package com.example.setoranmahasiswaapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.setoranmahasiswaapp.databinding.ItemRingkasanBinding
import com.example.setoranmahasiswaapp.model.RingkasanItem

class RingkasanAdapter : ListAdapter<RingkasanItem, RingkasanAdapter.VH>(diff) {

  companion object {
    private val diff = object: DiffUtil.ItemCallback<RingkasanItem>() {
      override fun areItemsTheSame(o: RingkasanItem, n: RingkasanItem) = o.label == n.label
      override fun areContentsTheSame(o: RingkasanItem, n: RingkasanItem) = o == n
    }
  }

  inner class VH(val b: ItemRingkasanBinding) : RecyclerView.ViewHolder(b.root) {
    @SuppressLint("SetTextI18n")
    fun bind(item: RingkasanItem) {
      b.tvLabel.text      = item.label
      b.tvWajib.text      = item.totalWajibSetor.toString()
      b.tvSudah.text      = item.totalSudahSetor.toString()
      b.tvBelum.text      = item.totalBelumSetor.toString()
      b.tvPersen.text     = "${item.persentaseProgresSetor}%"
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    VH(ItemRingkasanBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: VH, pos: Int) =
    holder.bind(getItem(pos))
}
