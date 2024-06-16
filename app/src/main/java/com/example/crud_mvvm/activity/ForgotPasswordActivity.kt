package com.example.crud_mvvm.ui.activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.crud_mvvm.databinding.ActivityForgotPasswordBinding
import com.example.crud_mvvm.factory.AuthViewModelFactory
import com.example.crud_mvvm.repository.AuthRepositoryImpl
import com.example.crud_mvvm.viewmodel.AuthViewModel

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = AuthRepositoryImpl()
        authViewModel = ViewModelProvider(this, AuthViewModelFactory(repository)).get(AuthViewModel::class.java)

        binding.forgotButton.setOnClickListener {
            val email = binding.forgotEmail.text.toString()
            authViewModel.forgotPassword(email)
        }

        authViewModel.authResult.observe(this) { result ->
            val (success, message) = result
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (success) {
                finish()
            }
        }
    }
}
