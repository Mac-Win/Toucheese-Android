package com.toucheese.app.data.network

import com.toucheese.app.data.model.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // 애플리케이션 전역 사용
object RetrofitClient {

    private const val BASE_URL = "https://api.toucheese-macwin.store/"

    /**
     * (1) OkHttpClient를 제공
     *     - 여기서 TokenAuthenticator를 등록
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        tokenAuthenticator: TokenAuthenticator // ← Hilt로 주입받을 TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(tokenAuthenticator) // ← 401 발생 시 토큰 갱신
            // .addInterceptor(...)        // 필요한 Interceptor가 있다면 추가
            .build()
    }

    /**
     * (2) 위에서 만든 OkHttpClient로 Retrofit 생성
     */
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // ← OkHttpClient 주입
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService = retrofit.create(HomeService::class.java)

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService = retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideQnaService(retrofit: Retrofit): QnaService = retrofit.create(QnaService::class.java)

    @Singleton
    @Provides
    fun provideBookScheduleService(retrofit: Retrofit): BookScheduleService = retrofit.create(BookScheduleService::class.java)

    @Singleton
    @Provides
    fun provideSignUpService(retrofit: Retrofit): SignUpService = retrofit.create(SignUpService::class.java)
}