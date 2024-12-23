package com.toucheese.app.data.repository

import com.toucheese.app.data.model.SocialLogin.Kakao.KakaoAuthCallbackResponse
import com.toucheese.app.data.model.SocialLogin.SocialLoginResponse
import com.toucheese.app.data.model.SocialLogin.SocialLoginRequest
import com.toucheese.app.data.model.SocialLogin.UpdateMemberInfoRequest
import com.toucheese.app.data.model.login.Login
import com.toucheese.app.data.network.LoginService
import okhttp3.Headers
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: LoginService){
    // -------- 회원 API --------

    // 회원 로그인
    suspend fun requestLogin(login: Login) = apiService.requestLogin(login)

    // 카카오 로그인 (POST)
    suspend fun requestKakaoLogin(socialLoginRequest: SocialLoginRequest): Response<SocialLoginResponse> {
        return apiService.kakaoLogin(socialLoginRequest)
    }

    // 카카오 로그인 콜백 (GET)
    suspend fun requestKakaoLoginCallback(
        code:String
    ): Response<KakaoAuthCallbackResponse> {
        return apiService.kakaoLoginCallback(code)
    }


    // 추가 정보 업데이트 (PUT)
    suspend fun updateMemberInfo(token: String, request: UpdateMemberInfoRequest):Response<Unit>{
        return apiService.updateMemberInfo("Bearer $token", request)
    }


}