package com.example.setoranmahasiswaapp.model

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("setoran")
	val setoran: Setoran,

	@field:SerializedName("info")
	val info: Info
)