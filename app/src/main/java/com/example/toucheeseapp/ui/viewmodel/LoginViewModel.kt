package com.example.toucheeseapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toucheeseapp.data.model.login.Login
import com.example.toucheeseapp.data.repository.LoginRepository
import com.example.toucheeseapp.data.token_manager.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository): ViewModel() {

    // 로그인 여부 확인
    fun isLoggedIn(tokenManager: TokenManager): Boolean{
        val token = tokenManager.getAccessToken()
        return !token.isNullOrEmpty()
    }

    // 회원 로그인
    fun requestLogin(tokenManager: TokenManager, email: String, password: String){
        val loginRequest = Login(email, password)
        viewModelScope.launch {
            try {
                val response = repository.requestLogin(loginRequest)
                if (response.isSuccessful) { // 응답 성공
                    // Authorization 헤더에서 토큰 추출
                    val authorizatioinHeader = response.headers()["Authorization"]
                    val token = authorizatioinHeader?.removePrefix("Bearer ")

                    if (token != null) {
                        tokenManager.saveAccessToken(token)
                        Log.d(TAG, "Access Token saved: $token")
                    } else {
                        Log.d(TAG, "Authorization header is missing or invalid")
                    }
                } else { // 응답 실패
                    Log.d(TAG, "Login failed: ${response.code()} - ${response.message()}")
                }
            } catch (error: Exception) {
                Log.d(TAG, "Login Error: ${error.message}")
            }
        }
    }

}