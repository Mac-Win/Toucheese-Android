package com.toucheese.app.data.network

import com.toucheese.app.data.model.sign_up.SignUpData
import retrofit2.http.Body
import retrofit2.http.POST


interface SignUpService {
    @POST("v1/members/signup")
    suspend fun requestSingUp(
        @Body data: SignUpData
    )
}