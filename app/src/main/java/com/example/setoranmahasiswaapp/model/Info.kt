package com.example.setoranmahasiswaapp.model

import com.google.gson.annotations.SerializedName

data class Info(

	@field:SerializedName("nim")
	val nim: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("angkatan")
	val angkatan: String,

	@field:SerializedName("dosen_pa")
	val dosenPa: DosenPa,

	@field:SerializedName("semester")
	val semester: Int,

	@field:SerializedName("email")
	val email: String
)