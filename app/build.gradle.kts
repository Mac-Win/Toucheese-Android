plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.toucheese.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.toucheese.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 11
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // local.properties에서 kakao_native_app_key 읽어오기
        val kakaoNativeAppKey: String = if (project.hasProperty("kakao_native_app_key")) {
            project.property("kakao_native_app_key") as String
        } else {
            ""
        }

        // BuildConfig에 Kakao 앱키 추가
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"$kakaoNativeAppKey\"")

        // 네이버 클라이언트 아이디 추가
        val naverClientId: String = if (project.hasProperty("naver_client_id")){
            project.property("naver_client_id") as String
        } else {
            ""
        }

        // 네이버 클라이언트 시크릿 추가
        val naverClientSecret: String = if (project.hasProperty("naver_client_secret")) {
            project.property("naver_client_secret") as String
        } else {
            ""
        }

        // BuildConfig에 네이버 클라이언트 아이디 추가
        buildConfigField("String", "NAVER_CLIENT_ID", "\"$naverClientId\"")
        buildConfigField("String", "NAVER_CLIENT_SECRET", "\"$naverClientSecret\"")
    }

    buildTypes {

        debug {
            // ProGuard 활성화
            isMinifyEnabled = false
            // Resource 축소
            isShrinkResources = false
            proguardFiles(
                // 기본 ProGuard 설정
                getDefaultProguardFile("proguard-android-optimize.txt"),
                // 프로젝트에 필요한 ProGuard 설정
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api.toucheese-macwin.store/\"")
            buildConfigField("Boolean", "ENABLE_LOG", "true") // 디버그 로그 활성화
        }

        release {
            // ProGuard 활성화
            isMinifyEnabled = true
            // Resource 축소
            isShrinkResources = true
            proguardFiles(
                // 기본 ProGuard 설정
                getDefaultProguardFile("proguard-android-optimize.txt"),
                // 프로젝트에 필요한 ProGuard 설정
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api.toucheese-macwin.store/\"") // 운영 URL
            buildConfigField("Boolean", "ENABLE_LOG", "false") // 릴리스에서 로그 비활성화
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson.v290)
    implementation(libs.converter.scalars.v290)
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    // Coil
    implementation("io.coil-kt.coil3:coil-compose:3.0.3")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.3")
    // Compose Calendar
    implementation("io.github.boguszpawlowski.composecalendar:composecalendar:1.3.0")
    implementation("io.github.boguszpawlowski.composecalendar:kotlinx-datetime:1.3.0")

    //KaKao
    implementation ("com.kakao.sdk:v2-all:2.20.0") // 전체 모듈 설치

    // Naver SDK
    implementation ("com.navercorp.nid:oauth:5.10.0")

}





// Allow references to generated code
kapt {
    correctErrorTypes = true
}