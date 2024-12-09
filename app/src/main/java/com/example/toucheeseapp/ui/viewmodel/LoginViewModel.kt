package com.example.toucheeseapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.toucheeseapp.data.model.login.Login
import com.example.toucheeseapp.data.repository.LoginRepository
import com.example.toucheeseapp.data.token_manager.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    // 현재 로그인한 사람의 아이디
    private val _memberId = MutableStateFlow(0)
    val memberId: StateFlow<Int> = _memberId

    // 현재 로그인한 사람의 이름
    private val _memberName = MutableStateFlow("")
    val memberName: StateFlow<String> = _memberName

    // 로그인 여부 확인
    fun isLoggedIn(tokenManager: TokenManager): Boolean {
        val token = tokenManager.getAccessToken()
        return !token.isNullOrEmpty()
    }

    // 회원 로그인
    suspend fun requestLogin(tokenManager: TokenManager, email: String, password: String) {
        val loginRequest = Login(email, password)
        try {
            val response = repository.requestLogin(loginRequest)
            if (response.isSuccessful) { // 응답 성공
                // Authorization 헤더에서 토큰 추출
                val authorizationHeader = response.headers()["Authorization"]
                val token = authorizationHeader?.removePrefix("Bearer ")
                val body = response.body()

                Log.d(TAG, "body: ${body}")
                if (token != null && body != null) {
                    // 토큰 저장
                    tokenManager.saveAccessToken(token)
                    // 멤버 아이디 저장
                    _memberId.value = body.memberId
                    // 멤버 이름 저장
                    _memberName.value = body.name

                    Log.d(TAG, "Access Token saved: $token")
                    Log.d(TAG, "멤버 아이디: ${memberId.value}")
                    Log.d(TAG, "멤버 이름: ${memberName.value}")
                } else {
                    Log.d(TAG, "Authorization header is missing or invalid")
                }
            } else { // 응답 실패
                Log.d(TAG, "Login failed: ${response.code()} - ${response.message()}")
            }
//                tokenManager.clearAccessToken()
            Log.d(TAG, "token = ${tokenManager.getAccessToken()}")
        } catch (error: Exception) {
            Log.d(TAG, "Login Error: ${error.message}")
        }
    }

    // 멤버 아이디 조회
    fun getMemberId(): Int = memberId.value
}