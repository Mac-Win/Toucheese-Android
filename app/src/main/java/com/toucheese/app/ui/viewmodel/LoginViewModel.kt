package com.toucheese.app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toucheese.app.data.model.SocialLogin.SocialLoginRequest
import com.toucheese.app.data.model.SocialLogin.SocialLoginResponse
import com.toucheese.app.data.model.SocialLogin.UpdateMemberInfoRequest
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

    // 현재 로그인한 사람의 전화번호
    private val _memberPhoneNumber = MutableStateFlow("")
    val memberPhoneNumber: StateFlow<String> = _memberPhoneNumber

    // ------------- 카카오 로그인 StateFlow -------------
    private val _kakaoLoginState = MutableStateFlow<KakaoLoginUiState>(KakaoLoginUiState.Idle)
    val kakaoLoginState: StateFlow<KakaoLoginUiState> = _kakaoLoginState

    // ------------- 추가 정보 업데이트 StateFlow -------------
    private val _updateInfoState = MutableStateFlow<UpdateInfoUiState>(UpdateInfoUiState.Idle)
    val updateInfoState: StateFlow<UpdateInfoUiState> = _updateInfoState


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

    // 추가 정보 입력
    fun updateMemberInfo(
        name: String,
        phoneNumber: String,
        tokenManager: TokenManager
    ) {
        viewModelScope.launch {
            if (name.isEmpty() || phoneNumber.isEmpty()) {
                _updateInfoState.value = UpdateInfoUiState.Error("모든 필드를 입력해주세요.")
                return@launch
            }

            _updateInfoState.value = UpdateInfoUiState.Loading

            try {
                // 요청 객체 생성
                val request = UpdateMemberInfoRequest(name = name, phone = phoneNumber)

                // 1. Access Token 가져오기 (요청 전에 필요)
                val token = tokenManager.getAccessToken()
                Log.d(TAG, "Fetched Access Token: $token")
                if (token.isNullOrEmpty()) {
                    _updateInfoState.value = UpdateInfoUiState.Error("인증 토큰이 없습니다. 다시 로그인 해주세요.")
                    return@launch
                }

                // 2. API 호출 (토큰을 사용하여 요청)
                val response: Response<Unit> = repository.updateMemberInfo(token, request)

                if (response.isSuccessful) {
                    _updateInfoState.value = UpdateInfoUiState.Success
                    Log.d(TAG, "추가 정보 업데이트 성공")

                    // 6. 사용자 정보 업데이트
                    _memberName.value = name
                    _memberPhoneNumber.value = phoneNumber

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "Update Member Info failed: ${response.code()} - ${response.message()} - $errorBody")
                    _updateInfoState.value = UpdateInfoUiState.Error("업데이트 실패: ${response.message()}")
                }
                Log.d(TAG, "Current token after updateMemberInfo: ${tokenManager.getAccessToken()}")
            } catch (e: Exception) {
                Log.e(TAG, "Update Member Info Exception: ${e.message}")
                _updateInfoState.value = UpdateInfoUiState.Error("예외 발생: ${e.message}")
            }
        }
    }

    // -------------------------------
    // (3) 카카오 로그인(2단계)
    // -------------------------------
    //  1) POST(kakaoAccessToken, kakaoIdToken)
    //  2) 서버에서 받은 응답 처리
    // -------------------------------
    fun requestKakaoLogin(
        tokenManager: TokenManager,
        kakaoAccessToken: String,
        kakaoIdToken: String
    ) {
        Log.d(TAG, "requestKakaoLogin 호출")
        viewModelScope.launch {
            if (kakaoAccessToken.isEmpty() || kakaoIdToken.isEmpty()) {
                Log.d(TAG, "카카오 토큰(Access / ID)이 비어 있습니다.")
                _kakaoLoginState.value = KakaoLoginUiState.Error("카카오 토큰이 비어 있습니다.")
                return@launch
            }

            _kakaoLoginState.value = KakaoLoginUiState.Loading

            try {
                // 1) 카카오 로그인 POST 요청
                val socialLoginRequest = SocialLoginRequest(
                    accessToken = kakaoAccessToken,
                    idToken = kakaoIdToken,
                    deviceId = tokenManager.getDeviceId() ?: "", // deviceId를 TokenManager에서 가져옴 로그인 안되면 이거 빈값으로 넣고 다시 매서드 불러오셈
                    platform = "KAKAO" // 플랫폼 명확히 지정
                )

                val postResponse: Response<SocialLoginResponse> =
                    repository.requestKakaoLogin(socialLoginRequest)

                if (postResponse.isSuccessful) {
                    val postBody = postResponse.body()
                    Log.d(TAG, "KakaoLogin POST body: $postBody")

                    if (postBody != null){
                        // 1. Authorization 헤더에서 Access Token 추출 및 저장
                        val authorizationHeader = postResponse.headers() ["Authorization"]
                        val accessToken = authorizationHeader?.removePrefix("Bearer ")

                        if (!accessToken.isNullOrEmpty()) {
                            tokenManager.saveAccessToken(accessToken)
                        } else {
                            _kakaoLoginState.value = KakaoLoginUiState.Error("Authorization 헤더에 유효한 토큰이 없습니다.")
                        }
                    }

                    if (postBody != null) {
                        // 서버에서 반환한 리프레시 토큰 저장
                        if (postBody.refreshToken.isNotEmpty()) {
                            tokenManager.saveRefreshToken(postBody.refreshToken)
                            Log.d(TAG, "Refresh Token saved: ${postBody.refreshToken}")
                        }

                        // deviceId 저장
                        if (postBody.deviceId.isNotEmpty()) {
                            tokenManager.saveDeviceId(postBody.deviceId)
                            Log.d(TAG, "Device ID saved: ${postBody.deviceId}")
                        }


                        // 로그인 상태 업데이트
                        _kakaoLoginState.value = KakaoLoginUiState.Success(postBody)

                        if (postBody.isFirstLogin) {
                            // 첫 로그인 시 추가 정보 입력 필요
                            Log.d(
                                TAG,
                                "First login detected. Member ID: ${postBody.memberId}"
                            )
                            // 추가 정보 입력 화면으로 이동하는 로직 추가
                            // 예시:
                            // navigateToAdditionalInfoScreen()
                        } else {
                            // 기존 사용자 처리
                            _memberId.value = postBody.memberId
                            _memberName.value = postBody.nickname
                            Log.d(
                                TAG,
                                "Existing user. Member ID: ${postBody.memberId}, Nickname: ${postBody.nickname}"
                            )
                            // 홈 화면으로 이동하는 로직 추가
                            // 예시:
                            // navigateToHomeScreen()
                        }
                    } else {
                        _kakaoLoginState.value =
                            KakaoLoginUiState.Error("Kakao Login POST body is null.")
                    }
                } else {
                    val errorBody = postResponse.errorBody()?.string()
                    Log.e(
                        TAG,
                        "Kakao Login POST failed: ${postResponse.code()} - ${postResponse.message()} - $errorBody"
                    )
                    _kakaoLoginState.value =
                        KakaoLoginUiState.Error("Kakao Login POST failed: ${postResponse.code()} - ${postResponse.message()}\n$errorBody")
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
    data class Success(val data: SocialLoginResponse?) : KakaoLoginUiState()
    data class Error(val msg: String) : KakaoLoginUiState()
}

// 추가 정보 업데이트 상태 관리용 Sealed Class
sealed class UpdateInfoUiState {
    object Idle : UpdateInfoUiState()
    object Loading : UpdateInfoUiState()
    object Success : UpdateInfoUiState()
    data class Error(val msg: String) : UpdateInfoUiState()
}