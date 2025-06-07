package com.example.setoranmahasiswaapp.model

import com.google.gson.annotations.SerializedName

data class DetailItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("nama_arab")
	val namaArab: String,

	@field:SerializedName("external_id")
	val externalId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("sudah_setor")
	val sudahSetor: Boolean,

	@field:SerializedName("info_setoran")
	val infoSetoran: Any
)