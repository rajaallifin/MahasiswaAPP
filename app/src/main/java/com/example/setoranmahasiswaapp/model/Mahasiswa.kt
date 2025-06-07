package com.example.setoranmahasiswaapp.model

import com.google.gson.annotations.SerializedName

data class Mahasiswa(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("response")
	val response: Boolean,

	@field:SerializedName("message")
	val message: String
)