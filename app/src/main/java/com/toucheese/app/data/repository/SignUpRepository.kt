package com.toucheese.app.data.repository

import com.toucheese.app.data.model.sign_up.SignUpData
import com.toucheese.app.data.network.SignUpService
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val apiService: SignUpService
) {

    // 회원가입 요청
    suspend fun requestSignUp(data: SignUpData) = apiService.requestSingUp(data)

}