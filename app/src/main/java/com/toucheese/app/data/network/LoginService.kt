package com.toucheese.app.data.network

import com.toucheese.app.data.model.SocialLogin.Kakao.KakaoAuthCallbackResponse
import com.toucheese.app.data.model.SocialLogin.SocialLoginRequest
import com.toucheese.app.data.model.SocialLogin.SocialLoginResponse
import com.toucheese.app.data.model.SocialLogin.UpdateMemberInfoRequest
import com.toucheese.app.data.model.login.Login
import com.toucheese.app.data.model.login.LoginResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LoginService {
    // -------- 회원 API --------

    // 회원 로그인
    @POST("v1/members")
    suspend fun requestLogin(@Body login: Login): Response<LoginResponseBody>

    // 카카오 로그인
    @POST("v1/auth/kakao")
    suspend fun kakaoLogin(@Body request: SocialLoginRequest): Response<SocialLoginResponse>

    // 카카오 로그인 콜백 (GET)
    @GET("v1/auth/kakao/callback")
    suspend fun kakaoLoginCallback(@Query("code") code: String): Response<KakaoAuthCallbackResponse>

    // 추가 정보 업데이트
    @PUT("v1/members")
    suspend fun updateMemberInfo(@Header("Authorization") authToken: String, @Body request: UpdateMemberInfoRequest): Response<Unit>

}