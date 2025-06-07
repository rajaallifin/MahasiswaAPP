package com.example.setoranmahasiswaapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.setoranmahasiswaapp.adapter.DetailAdapter
import com.example.setoranmahasiswaapp.adapter.RingkasanAdapter
import com.example.setoranmahasiswaapp.api.MahasiswaRetrofiInstance
import com.example.setoranmahasiswaapp.databinding.ActivityMainBinding
import com.example.setoranmahasiswaapp.model.Mahasiswa
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ringkasanAdapter: RingkasanAdapter
    private lateinit var detailAdapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scroll)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.downloadSetoran.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Download PDF")
                .setMessage("Do you want to download your Kartu Muroja’ah PDF?")
                .setPositiveButton("Yes") { dialog, _ ->
                    dialog.dismiss()
                    downloadPdf()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        ringkasanAdapter = RingkasanAdapter()
        detailAdapter    = DetailAdapter()
        binding.rvRingkasan.layoutManager = LinearLayoutManager(this)
        binding.rvRingkasan.adapter       = ringkasanAdapter
        binding.rvDetail.layoutManager    = LinearLayoutManager(this)
        binding.rvDetail.adapter          = detailAdapter

        MahasiswaRetrofiInstance.create(this)
            .mahasiswaSetoran()
            .enqueue(object : Callback<Mahasiswa> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<Mahasiswa?>,
                    response: Response<Mahasiswa?>
                ) {
                    if (!response.isSuccessful || response.body() == null) {
                        Toast.makeText(this@MainActivity,
                            "Error ${response.code()}", Toast.LENGTH_LONG).show()
                        return
                    }
                    val body = response.body()!!.data

                    // populate header info
                    with(binding) {
                        tvNama.text     = body.info.nama
                        tvNim.text      = body.info.nim
                        tvAngkatan.text = body.info.angkatan
                        tvSemester.text = body.info.semester.toString()
                        tvDosenPa.text  = "${body.info.dosenPa.nama} (${body.info.dosenPa.nip})"

                        tvWajib.text    = body.setoran.infoDasar.totalWajibSetor.toString()
                        tvSudah.text    = body.setoran.infoDasar.totalSudahSetor.toString()
                        tvBelum.text    = body.setoran.infoDasar.totalBelumSetor.toString()
                        tvPersen.text   = "${body.setoran.infoDasar.persentaseProgresSetor}%"
                    }

                    // feed adapters
                    ringkasanAdapter.submitList(body.setoran.ringkasan)
                    detailAdapter.submitList(body.setoran.detail)
                }

                override fun onFailure(
                    call: Call<Mahasiswa?>,
                    t: Throwable
                ) {
                    Toast.makeText(this@MainActivity,
                        "Network error: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun downloadPdf() {
        MahasiswaRetrofiInstance.create(this@MainActivity).downloadetoran()
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    if (!response.isSuccessful || response.body() == null) {
                        Toast.makeText(this@MainActivity, "Server error ${response.code()}", Toast.LENGTH_LONG).show()
                        return
                    }
                    val saved = writeResponseBodyToDisk(response.body()!!)
                    val msg = if (saved)
                        "Saved to Downloads/kartu_murojaah.pdf"
                    else
                        "Failed to save file"
                    Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()
                }

                override fun onFailure(
                    call: Call<ResponseBody?>,
                    t: Throwable
                ) {
                    Toast.makeText(this@MainActivity, "Download failed: ${t.message}", Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun writeResponseBodyToDisk(body: ResponseBody): Boolean {
        return try {
            val downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloads.exists()) downloads.mkdirs()
            val random = UUID.randomUUID()
            val outFile = File(downloads, "kartu_murojaah_$random.pdf")
            body.byteStream().use { input ->
                FileOutputStream(outFile).use { output ->
                    val buffer = ByteArray(4096)
                    var read: Int
                    while (input.read(buffer).also { read = it } != -1) {
                        output.write(buffer, 0, read)
                    }
                    output.flush()
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}