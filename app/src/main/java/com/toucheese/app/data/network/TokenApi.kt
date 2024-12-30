package com.toucheese.app.data.network

import com.toucheese.app.data.model.token.TokenRequest
import com.toucheese.app.data.model.token.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

    interface TokenApi {
        @POST("v1/tokens/reissue")
        suspend fun reissueToken(
            @Body request: TokenRequest
        ): Response<TokenResponse>
    }
