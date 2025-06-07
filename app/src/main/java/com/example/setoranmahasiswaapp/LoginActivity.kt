package com.example.setoranmahasiswaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.setoranmahasiswaapp.api.LoginRetrofitInstance
import com.example.setoranmahasiswaapp.databinding.ActivityLoginBinding
import com.example.setoranmahasiswaapp.model.TokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupButton()
    }

    private fun setupButton() = binding.apply {
        btnLogin.setOnClickListener {
            attemptLogin()
        }
    }

    private fun attemptLogin() = binding.apply {
        val nimText = inputNim.text.toString().trim()
        val pwdText = password.text.toString().trim()
        if (nimText.isEmpty()) {
            inputNim.error = "Email wajib diisi"
            return@apply
        }
        if (pwdText.isEmpty()) {
            password.error = "Kata sandi wajib diisi"
            return@apply
        }
        btnLogin.isEnabled = false

        LoginRetrofitInstance.api.login(
            username = nimText,
            password = pwdText
        ).enqueue(object: Callback<TokenResponse> {
            override fun onResponse(
                call: Call<TokenResponse>,
                response: Response<TokenResponse>
            ) {
                btnLogin.isEnabled = true
                if (response.isSuccessful) {
                    val token = response.body()!!.accessToken
                    // simpan ke SharedPreferences misalnya
                    getSharedPreferences("prefs", MODE_PRIVATE).edit {
                        putString("ACCESS_TOKEN", token)
                    }
                    navigateToMain()
                } else {
                    Toast.makeText(this@LoginActivity,
                        "Login gagal: ${response.code()}",
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                btnLogin.isEnabled = true
                Toast.makeText(this@LoginActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun navigateToMain() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java)
            .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK })
        finish()
    }
}