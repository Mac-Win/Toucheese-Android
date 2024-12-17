package com.toucheese.app.data.network

import com.toucheese.app.data.model.login.Login
import com.toucheese.app.data.model.login.LoginResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    // -------- 회원 API --------

    // 회원 로그인
    @POST("v1/members")
    suspend fun requestLogin(@Body login: Login): Response<LoginResponseBody>

}