package com.example.setoranmahasiswaapp.model

import com.google.gson.annotations.SerializedName

data class Setoran(

	@field:SerializedName("log")
	val log: List<Any>,

	@field:SerializedName("info_dasar")
	val infoDasar: InfoDasar,

	@field:SerializedName("detail")
	val detail: List<DetailItem>,

	@field:SerializedName("ringkasan")
	val ringkasan: List<RingkasanItem>
)