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

    // 비밀번호 상태
    private val _passwordState = MutableStateFlow("")
    val passwordState: StateFlow<String> = _passwordState

    // 비밀번호 유효성
    private val _isValidatePassword = MutableStateFlow(false)
    val isValidatePassword: StateFlow<Boolean> = _isValidatePassword

    // 비밀번호 확인 상태
    private val _matchingPasswordState = MutableStateFlow("")
    val matchingPasswordState: StateFlow<String> = _matchingPasswordState

    // 비밀번호 확인 여부
    private val _isMatchingPassword = MutableStateFlow(false)
    val isMatchingPassword: StateFlow<Boolean> = _isMatchingPassword

    // 이름 상태
    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState

    // 이름 유효성
    private val _isValidateName = MutableStateFlow(false)
    val isValidateName: StateFlow<Boolean> = _isValidateName

    init {
        viewModelScope.launch {
            // 이메일 유효성 검사
            _emailState
                .debounce(300) // 입력이 300ms만큼 멈춘 경우에만
                .collect { email: String ->
                    // email 유효성 검사
                    isValidateEmail()
                }
        }

        viewModelScope.launch {
            // 비밀번호 유효성 검사
            _passwordState
                .debounce(300)
                .collect{ password: String ->
                    // password 유효성 검사
                    isValidatePassword()
                }
        }

        viewModelScope.launch {
            // 비밀번호 확인 검사
            _matchingPasswordState
                .collect{ matchingPassword: String ->
                    // paasword 일치 검사
                    isMatchingPassword()
                }
        }

        viewModelScope.launch {
            // 이름 유효성 검사
            _nameState
                .debounce(300)
                .collect { name: String ->
                    // 이름 유효성 검사
                    isValidateName()
                }
        }

    }

    // 이메일 설정
    fun setEmail(email: String) {
        _emailState.value = email
    }

    // 비밀번호 설정
    fun setPassword(password: String){
        _passwordState.value = password
    }

    // 비밀번호 확인 설정
    fun setMatchingPassword(matchingPassword: String){
        _matchingPasswordState.value = matchingPassword
    }

    // 이름 설정
    fun setName(name: String){
        _nameState.value = name
    }

    // 회원 등록 데이터 초기화
    fun initData() {
        // 이메일 상태 초기화
        _emailState.value = ""
        // 이메일 유효성 초기화
        _isValidateEmail.value = false
    }

    // 이메일 유효성 검사
    private fun isValidateEmail() {
        /** 정규 표현식 내용
         * a-z, A-Z, 0-9까지의 영문자나 숫자 4개 이상
         * _, 소문자, 숫자, 하이픈 중 하나가 반복 가능
         * @ 뒤에 소문자, 대문자, 숫자 중 하나 이상 반복
         * .이 나온 후 소문자, 대문자, 숫자 중 하나 이상 반복
         */
        val emailPattern = "[a-zA-Z0-9]{4,}+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
        val isValidate = Pattern.matches(emailPattern, emailState.value.trim())
        _isValidateEmail.value = isValidate
        Log.d("SignUpViewModel", "이메일 유효성 검사 결과 : $isValidate")
    }

    // 비밀번호 유효성 검사
    private fun isValidatePassword(){
        /** 정규 표현식 내용
         * 소문자, 대문자, 특수문자의 조합으로 8글자 이상 20글자 이하
         */
        val pwPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@!%*#?&])[A-Za-z0-9$@!%*#?&]{8,20}$"
        val isValidate = Pattern.matches(pwPattern, passwordState.value.trim())
        _isValidatePassword.value = isValidate
        Log.d("SignUpViewModel", "비밀번호 유효성 검사 결과 : $isValidate")
    }

    // 비밀번호 확인 일치 여부
    private fun isMatchingPassword(){
        _isMatchingPassword.value = if (matchingPasswordState.value.isNotBlank()) {
            passwordState.value == matchingPasswordState.value
        } else false
        Log.d("SignUpViewModel", "입력된 비밀번호 확인 : ${matchingPasswordState.value}")
        Log.d("SignUpViewModel", "비밀번호 확인 여부 검사 결과: ${isMatchingPassword.value}")
    }

    // 이름 유효성 검사
    private fun isValidateName() {
        /** 정규 표현식 내용
         * 한글 2글자 ~ 4글자
         */
        val namePattern = "^[가-힣]{2,4}$"
        val isValidate = Pattern.matches(namePattern, nameState.value.trim())
        _isValidateName.value = isValidate
        Log.d("SignUpViewModel", "이름 입력값: ${nameState.value}")
        Log.d("SignUpViewModel", "이름 유효성 검사 결과: ${isValidateName.value}")
    }

}