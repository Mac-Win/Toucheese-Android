package com.toucheese.app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toucheese.app.data.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {

    // 이메일 상태
    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState

    // 이메일 유효성
    private val _isValidateEmail = MutableStateFlow(false)
    val isValidateEmail: StateFlow<Boolean> = _isValidateEmail

    init {
        viewModelScope.launch {
            // 이메일 유효성 검사
            _emailState
                .debounce(300) // 입력이 300ms만큼 멈춘 경우에만
                .collect { email: String ->
                    // email 유효성 검사
                    isValidateEmail()
                }

            // 비밀번호 유효성 검사
        }
    }

    // 이메일 설정
    fun setEmail(email: String) {
        _emailState.value = email
    }

    // 이메일 관련 초기화
    fun initEmail() {
        // 이메일 상태 초기화
        _emailState.value = ""
        // 이메일 유효성 초기화
        _isValidateEmail.value = false
    }

    // 이메일 유효성 검사
    private fun isValidateEmail() {
        // 입력된 내용이 있는경우
        /** 정규 표현식 내용
         * a-z, A-Z, 0-9까지의 영문자나 숫자 4개 이상
         * _, 소문자, 숫자, 하이픈 중 하나가 반복 가능
         * @ 뒤에 소문자, 대문자, 숫자 중 하나 이상 반복
         * .이 나온 후 소문자, 대문자, 숫자 중 하나 이상 반복
         */
        val isValidate = Pattern.matches("[a-zA-Z0-9]{4,}+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", emailState.value)
        _isValidateEmail.value = isValidate
        Log.d("SignUpViewModel", "이메일 유효성 검사 결과 : $isValidate")
    }

}