package com.example.crud_mvvm.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.crud_mvvm.databinding.ActivityLoginBinding
import com.example.crud_mvvm.factory.AuthViewModelFactory
import com.example.crud_mvvm.repository.AuthRepositoryImpl
import com.example.crud_mvvm.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = AuthRepositoryImpl()
        authViewModel = ViewModelProvider(this, AuthViewModelFactory(repository)).get(AuthViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            authViewModel.login(email, password)
        }
        binding.registerButttonText.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }
        binding.forgotpassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        authViewModel.authResult.observe(this) { result ->
            val (success, message) = result
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (success) {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }
}
