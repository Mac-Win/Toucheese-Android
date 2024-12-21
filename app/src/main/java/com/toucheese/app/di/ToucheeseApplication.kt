package com.toucheese.app.di

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToucheeseApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        // kakao SDK 초기화
        KakaoSdk.init(this, "0fbea0ec74d8b0b35ea699ee605d01b9")

        // Naver SDK 초기화
        NaverIdLoginSDK.initialize(
            this,
            "YttwkEwgWEHQtConXzt6",       // 네이버 개발자 센터에서 발급받은 Client ID
            "bAHyT6QhgT",   // 네이버 개발자 센터에서 발급받은 Client Secret
            "Toucheese"               // 애플리케이션 이름
        )
    }
}