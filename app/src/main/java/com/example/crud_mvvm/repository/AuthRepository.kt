package com.example.crud_mvvm.repository

import com.google.firebase.auth.FirebaseAuth

interface AuthRepository {
    fun signUp(email: String, password: String, callback: (Boolean, String?) -> Unit)
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit)
    fun forgotPassword(email: String, callback: (Boolean, String?) -> Unit)
}