package com.example.toucheeseapp.data.network

import com.example.toucheeseapp.data.model.login.Login
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    // -------- 회원 API --------

    // 회원 로그인
    @POST("v1/members")
    suspend fun requestLogin(@Body login: Login): Response<Unit> // 본문이 없으므로 Unit 사용

}