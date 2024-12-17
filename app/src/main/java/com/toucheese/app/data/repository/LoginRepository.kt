package com.toucheese.app.data.repository

import com.toucheese.app.data.model.login.Login
import com.toucheese.app.data.network.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: LoginService){
    // -------- 회원 API --------

    // 회원 로그인
    suspend fun requestLogin(login: Login) = apiService.requestLogin(login)

}