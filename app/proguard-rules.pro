# 기본 설정
-keepattributes LineNumberTable,SourceFile,Signature,*Annotation*,InnerClasses
-renamesourcefileattribute SourceFile

# Missing Rule 추가
-dontwarn com.google.errorprone.annotations.CanIgnoreReturnValue
-dontwarn com.google.errorprone.annotations.CheckReturnValue
-dontwarn com.google.errorprone.annotations.Immutable
-dontwarn com.google.errorprone.annotations.RestrictedApi
-dontwarn com.google.errorprone.annotations.MustBeClosed
-keep,allowobfuscation,allowshrinking class kotlinx.coroutines.flow.Flow
-keep class com.toucheese.app.data.model.** { *; }

# Retrofit
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class retrofit2.Call { *; }
-keep class retrofit2.converter.gson.** { *; }
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}

# Gson
-keep class com.google.gson.reflect.TypeToken { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keepclassmembers class * {
    @javax.inject.Inject <fields>;
    @javax.inject.Inject <methods>;
}
-keepnames class * {
    @javax.inject.Inject <init>(...);
}
-keep class dagger.hilt.internal.** { *; }
-keep class dagger.hilt.processor.** { *; }
-keep class dagger.hilt.codegen.** { *; }

# Kotlin
-keepclassmembers class kotlin.Metadata { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }

# Coil
-keep class coil.** { *; }
-keep class coil.request.** { *; }

# Navigation
-keep class androidx.navigation.** { *; }

# Compose
-keep class androidx.compose.** { *; }

# Navigation Compose
-keep class androidx.hilt.navigation.** { *; }

# 로그 유지
-keep class android.util.Log { *; }

# Kakao SDK 관련 Proguard 설정
-keep class com.kakao.** { *; }
-keep class com.kakao.sdk.** { *; }
-keep class com.kakao.sdk.auth.** { *; }
-keep class com.kakao.sdk.user.** { *; }
-keep class com.fasterxml.jackson.** { *; }
-keep class com.kakao.sdk.common.** { *; }
-keep class com.kakao.sdk.network.** { *; }
-keep class com.kakao.sdk.auth.model.** { *; }

# OkHttp 관련 Proguard 설정
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class okhttp3.logging.** { *; }
-keep interface okhttp3.logging.** { *; }

# Base64 관련
-keep class android.util.Base64 { *; }

# Cipher 및 암호화 관련
-keep class javax.crypto.** { *; }

# ProGuard 활성화 후 디버깅
-printmapping mapping.txt