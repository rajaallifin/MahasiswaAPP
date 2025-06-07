package com.example.setoranmahasiswaapp.model

import com.google.gson.annotations.SerializedName

data class InfoDasar(

	@field:SerializedName("total_sudah_setor")
	val totalSudahSetor: Int,

	@field:SerializedName("total_belum_setor")
	val totalBelumSetor: Int,

	@field:SerializedName("persentase_progres_setor")
	val persentaseProgresSetor: Float,

	@field:SerializedName("terakhir_setor")
	val terakhirSetor: String,

	@field:SerializedName("total_wajib_setor")
	val totalWajibSetor: Int,

	@field:SerializedName("tgl_terakhir_setor")
	val tglTerakhirSetor: Any
)