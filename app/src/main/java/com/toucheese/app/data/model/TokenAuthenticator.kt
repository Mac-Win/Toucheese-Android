package com.toucheese.app.data.model

import android.content.Context
import android.util.Log
import com.toucheese.app.BuildConfig
import com.toucheese.app.data.model.token.TokenRequest
import com.toucheese.app.data.network.TokenApi
import com.toucheese.app.data.token_manager.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tokenManager: TokenManager,   // SharedPreferences 등 관리
    private val tokenApi: TokenApi            // 토큰 재발급 API (Body로 전송)
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // 1) 무한루프 방지: 재발급 API 자체에서 401이 발생하면 재발급 로직 중첩 방지
        val isPathRefresh =
            response.request.url.toString() == BuildConfig.BASE_URL + "v1/tokens/reissue"

        // 2) 메인 API가 401을 리턴했고, 그 URL이 재발급 API가 아닐 때만 시도
        if (response.code == 401 && !isPathRefresh) {
            Log.d("TokenAuthenticator", "401 발생 & reissue 경로 아님 → 토큰 재발급 시도")

            val tokenRefreshSuccess = fetchUpdateToken()
            return if (tokenRefreshSuccess) {
                val newAccessToken = tokenManager.getAccessToken()
                Log.d("TokenAuthenticator", "새 토큰 발급 완료 → newAccessToken: $newAccessToken")

                // 새 토큰으로 Authorization 헤더 교체
                response.request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer $newAccessToken")
                    .build()
            } else {
                Log.e("TokenAuthenticator", "토큰 갱신 실패 → null 반환")
                null
            }
        }
        return null
    }

    /**
     * (A) refreshToken + deviceId를 Body로 전송하여 서버에서 토큰 재발급
     */
    private fun fetchUpdateToken(): Boolean {
        val refreshToken = tokenManager.getRefreshToken() ?: return false
        val deviceId = tokenManager.getDeviceId() ?: return false

        return runBlocking {
            try {
                // (1) Body로 보낼 요청 객체 생성
                val requestBody = TokenRequest(
                    deviceId = deviceId,
                    refreshToken = refreshToken
                )

                // (2) 서버에 토큰 재발급 요청
                val response = tokenApi.reissueToken(requestBody)
                if (response.isSuccessful) {
                    // (A) 응답 헤더에서 새 accessToken 추출
                    val headerValue = response.headers()["Authorization"]
                    val newAccessToken = headerValue?.removePrefix("Bearer ")?.trim()

                    // (B) 응답 Body 파싱
                    val body = response.body()
                    if (body != null) {
                        // 새 refreshToken, deviceId 저장
                        tokenManager.saveRefreshToken(body.refreshToken)
                        tokenManager.saveDeviceId(body.deviceId)

                        // 헤더에 새 accessToken이 있었다면 저장
                        if (!newAccessToken.isNullOrEmpty()) {
                            tokenManager.saveAccessToken(newAccessToken)
                            Log.d(
                                "TokenAuthenticator",
                                "AccessToken (from header): $newAccessToken"
                            )
                        } else {
                            Log.w("TokenAuthenticator", "No accessToken in header")
                        }
                        true
                    } else {
                        Log.e("TokenAuthenticator", "reissueToken 응답 바디 null")
                        false
                    }
                } else {
                    Log.e(
                        "TokenAuthenticator",
                        "reissueToken 실패 code=${response.code()} message=${response.message()}"
                    )
                    false
                }
            } catch (e: Exception) {
                Log.e("TokenAuthenticator", "reissueToken 예외: ${e.message}")
                false
            }
        }
    }
}