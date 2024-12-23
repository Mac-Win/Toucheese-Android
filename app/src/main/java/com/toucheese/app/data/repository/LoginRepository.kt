package com.toucheese.app.data.repository

import com.toucheese.app.data.model.SocialLogin.Kakao.KakaoAuthCallbackResponse
import com.toucheese.app.data.model.SocialLogin.Kakao.KakaoAuthResponse
import com.toucheese.app.data.model.SocialLogin.MemberInfoRequest
import com.toucheese.app.data.model.SocialLogin.MemberInfoResponse
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
    suspend fun requestKakaoLogin(
        kakaoAuthResponse: KakaoAuthResponse
    ): Response<KakaoAuthCallbackResponse> {
        return apiService.kakaoLogin(kakaoAuthResponse)
    }

    // 카카오 로그인 콜백 (GET)
    suspend fun requestKakaoLoginCallback(
        token: String
    ): Response<KakaoAuthCallbackResponse> {
        return apiService.kakaoLoginCallback(token)
    }


    // 추가 정보 업데이트 (서버 엔드포인트 없음 -> Mock 처리)
    suspend fun updateMemberInfo(token: String?, name: String, phoneNumber: String): Response<MemberInfoResponse> {
        // Mock 응답
        val mockResponse = MemberInfoResponse(
            memberId = 1234,
            name = name,
            nickname = "개개오",
            email = "kakao@ex1ample.com",
            phoneNumber = phoneNumber
        )
        return Response.success(mockResponse)
    }


//    // 추가 정보 업데이트
//    suspend fun updateMemberInfo(token: String?, name: String, phoneNumber: String): Response<MemberInfoResponse> {
//        val memberInfoRequest = MemberInfoRequest(name, phoneNumber)
//        return apiService.updateMemberInfo(token, memberInfoRequest)
//    }

}