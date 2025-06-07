package com.example.setoranmahasiswaapp.model

import com.google.gson.annotations.SerializedName

data class RingkasanItem(

	@field:SerializedName("total_sudah_setor")
	val totalSudahSetor: Int,

	@field:SerializedName("total_belum_setor")
	val totalBelumSetor: Int,

	@field:SerializedName("persentase_progres_setor")
	val persentaseProgresSetor: Float,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("total_wajib_setor")
	val totalWajibSetor: Int
)