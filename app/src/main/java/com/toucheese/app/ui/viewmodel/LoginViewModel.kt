package com.toucheese.app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toucheese.app.data.model.SocialLogin.Kakao.KakaoAuthCallbackResponse
import com.toucheese.app.data.model.SocialLogin.Kakao.KakaoAuthResponse
import com.toucheese.app.data.model.login.Login
import com.toucheese.app.data.repository.LoginRepository
import com.toucheese.app.data.token_manager.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
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

    // ------------- 카카오 로그인 StateFlow -------------
    private val _kakaoLoginState = MutableStateFlow<KakaoLoginUiState>(KakaoLoginUiState.Idle)
    val kakaoLoginState: StateFlow<KakaoLoginUiState> = _kakaoLoginState

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


    fun submitAdditionalInfo(tokenManager: TokenManager, name: String, phoneNumber: String) {
        viewModelScope.launch {
            val response = repository.updateMemberInfo(tokenManager.getAccessToken(), name, phoneNumber)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    _memberName.value = body.name
                    // 여기서도 실제로 서버 연동 시 Authorization 확인 후 로직 처리 가능
                    // 현재 Mock 상태이므로 바로 성공 처리
                    Log.d(TAG, "Additional Info saved: ${body.name}, ${body.phoneNumber}")
                }
            } else {
                Log.d(TAG, "Additional Info Update Failed: ${response.code()}")
            }
        }
    }


//    // 추가 정보
//    suspend fun submitAdditionalInfo(name: String, phoneNumber: String) {
//        val response = repository.updateMemberInfo(
//            token = tokenManager.getAccessToken(),
//            name = name,
//            phoneNumber = phoneNumber
//        )
//        if (response.isSuccessful) {
//            // 업데이트 성공 시 홈화면으로 이동
//            // UI단에서 state 변화 감지 후 navController.navigate("home") 호출 가능
//            _memberName.value = name
//            // 성공 시 navigate 로직은 Screen에서 감지
//        } else {
//            // 업데이트 실패 처리(에러 메시지 표시 등)
//        }
//    }

    // -------------------------------
    // (3) 카카오 로그인(2단계)
    // -------------------------------
    //  1) POST(kakaoAccessToken, kakaoIdToken)
    //  2) GET(token=서버에서 돌려준 accessToken 등)
    // -------------------------------
    fun requestKakaoLogin(tokenManager: TokenManager, kakaoAccessToken: String, kakaoIdToken: String) {
        viewModelScope.launch {
            if (kakaoAccessToken.isEmpty() || kakaoIdToken.isEmpty()) {
                Log.d(TAG, "카카오 토큰(Access / ID)이 비어 있습니다.")
                return@launch
            }

            _kakaoLoginState.value = KakaoLoginUiState.Loading

            try {
                // 1) 카카오 로그인 POST
                val postResponse: Response<KakaoAuthCallbackResponse> = repository.requestKakaoLogin(
                    KakaoAuthResponse(
                        accessToken = kakaoAccessToken,
                        idToken = kakaoIdToken
                    )
                )

                if (postResponse.isSuccessful) {
                    val postBody = postResponse.body()
                    Log.d(TAG, "KakaoLogin POST body: $postBody")

                    // 일단 서버에서 accessToken을 새로 준다고 가정(예: JWT)
                    // 실제로는 KakaoAuthCallbackResponse 안에 토큰 필드가 없으므로,
                    // "콜백용 token"이 따로 있거나, 혹은 기존 kakaoAccessToken을 다시 쓸 수도 있습니다.

                    // 예시) 일단은 "kakaoAccessToken" 자체를 callback token으로 쓴다고 가정
                    //      실제로는 postBody에 callbackToken이 있을 수도 있으므로 상황에 맞춰 수정
                    val callbackToken = kakaoAccessToken

                    // 2) 콜백 GET
                    val getResponse: Response<KakaoAuthCallbackResponse> = repository.requestKakaoLoginCallback(callbackToken)
                    if (getResponse.isSuccessful) {
                        val getBody = getResponse.body()
                        Log.d(TAG, "KakaoLogin GET body: $getBody")

                        if (getBody != null) {
                            // 콜백 결과 처리
                            _kakaoLoginState.value = KakaoLoginUiState.Success(getBody)
                            // 필요하다면 isFirstLogin, nickname 등을 이용해 추가 로직
                            // ex) _memberName.value = getBody.nickname
                        } else {
                            _kakaoLoginState.value = KakaoLoginUiState.Error("Kakao Login Callback body is null.")
                        }
                    } else {
                        _kakaoLoginState.value = KakaoLoginUiState.Error("Kakao Login Callback failed: ${getResponse.message()}")
                    }

                } else {
                    _kakaoLoginState.value = KakaoLoginUiState.Error("Kakao Login POST failed: ${postResponse.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _kakaoLoginState.value = KakaoLoginUiState.Error("Exception: ${e.message}")
            }
        }
    }
}


// -------------------------------
// 카카오 로그인 상태 UI 관리용 Sealed Class
// -------------------------------
sealed class KakaoLoginUiState {
    object Idle : KakaoLoginUiState()
    object Loading : KakaoLoginUiState()
    data class Success(val data: KakaoAuthCallbackResponse) : KakaoLoginUiState()
    data class Error(val msg: String) : KakaoLoginUiState()
}
