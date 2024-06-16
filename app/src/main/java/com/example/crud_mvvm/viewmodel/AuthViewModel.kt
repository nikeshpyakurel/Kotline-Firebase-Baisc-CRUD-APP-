package com.example.crud_mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crud_mvvm.repository.AuthRepository

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    val authResult = MutableLiveData<Pair<Boolean, String?>>()

    fun signUp(email: String, password: String) {
        repository.signUp(email, password) { success, message ->
            authResult.value = Pair(success, message)
        }
    }

    fun login(email: String, password: String) {
        repository.login(email, password) { success, message ->
            authResult.value = Pair(success, message)
        }
    }

    fun forgotPassword(email: String) {
        repository.forgotPassword(email) { success, message ->
            authResult.value = Pair(success, message)
        }
    }
}
