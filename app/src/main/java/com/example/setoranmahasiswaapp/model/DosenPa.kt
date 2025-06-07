package com.example.setoranmahasiswaapp.model

import com.google.gson.annotations.SerializedName

data class DosenPa(

	@field:SerializedName("nip")
	val nip: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("email")
	val email: String
)