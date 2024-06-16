package com.example.crud_mvvm.repository

import com.example.crud_mvvm.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

class AuthRepositoryImpl : AuthRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun signUp(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Sign Up Successful")
            } else {
                callback(false, it.exception?.message)
            }
        }
    }

    override fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Login Successful")
            } else {
                callback(false, it.exception?.message)
            }
        }
    }

    override fun forgotPassword(email: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Password reset email sent")
            } else {
                callback(false, it.exception?.message)
            }
        }
    }
}
