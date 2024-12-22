package com.toucheese.app.data.network

import com.toucheese.app.data.model.SocialLogin.Kakao.KakaoAuthCallbackResponse
import com.toucheese.app.data.model.SocialLogin.Kakao.KakaoAuthResponse
import com.toucheese.app.data.model.login.Login
import com.toucheese.app.data.model.login.LoginResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    // -------- 회원 API --------

    // 회원 로그인
    @POST("v1/members")
    suspend fun requestLogin(@Body login: Login): Response<LoginResponseBody>

    // 카카오 로그인
    @POST("v1/auth/kakao")
    suspend fun kakaoLogin(@Body KakaoAuthResponse: KakaoAuthResponse): Response<KakaoAuthCallbackResponse>

    // 카카오 로그인 콜백 (GET)
    @GET("v1/auth/kakao/callback")
    suspend fun kakaoLoginCallback(@Query("token") token: String): Response<KakaoAuthCallbackResponse>


}