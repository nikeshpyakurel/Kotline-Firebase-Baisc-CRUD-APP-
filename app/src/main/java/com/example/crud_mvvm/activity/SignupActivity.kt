package com.example.crud_mvvm.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.crud_mvvm.factory.AuthViewModelFactory
import com.example.crud_mvvm.databinding.ActivitySignupAcitivityBinding
import com.example.crud_mvvm.repository.AuthRepositoryImpl
import com.example.crud_mvvm.viewmodel.AuthViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupAcitivityBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupAcitivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = AuthRepositoryImpl()
        authViewModel = ViewModelProvider(this, AuthViewModelFactory(repository)).get(AuthViewModel::class.java)

        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            authViewModel.signUp(email, password)
        }
        binding.loginButtonText.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        authViewModel.authResult.observe(this) { result ->
            val (success, message) = result
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (success) {
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }
    }
}
