package com.example.project

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (isValidCredentials(username, password)) {
                saveLoggedInUser(username)
                navigateToMainActivity()
            } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
         val landlordUsername = "landlord"
        val landlordPassword = "landlord123"

        val tenantUsername = "tenant"
        val tenantPassword = "tenant123"

        var isLandlord = false

        if (username == landlordUsername && password == landlordPassword) {
            isLandlord = true
        }

         val editor = sharedPreferences.edit()
        editor.putBoolean("IS_LANDLORD", isLandlord)
        editor.apply()

        return isLandlord || (username == tenantUsername && password == tenantPassword)
    }

    private fun saveLoggedInUser(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("USERNAME", username)
        editor.apply()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
        finish()   }
}
