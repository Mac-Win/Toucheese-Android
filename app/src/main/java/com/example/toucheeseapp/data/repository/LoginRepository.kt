package com.example.toucheeseapp.data.repository

import com.example.toucheeseapp.data.model.login.Login
import com.example.toucheeseapp.data.network.LoginService
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: LoginService){
    // -------- 회원 API --------

    // 회원 로그인
    suspend fun requestLogin(login: Login) = apiService.requestLogin(login)

}