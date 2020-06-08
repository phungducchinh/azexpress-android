package com.app.data.remote.api.app.user

import com.app.data.remote.api.app.user.login.LoginRequest
import com.app.data.remote.api.app.user.login.LoginResponse
import com.app.data.remote.api.app.user.signup.SignUpRequest
import com.app.data.remote.api.app.user.signup.SignUpResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST("accounts/login")
    fun login(@Body request: LoginRequest): Observable<Response<LoginResponse>>

    @POST("accounts/signup")
    fun signUp(@Body request: SignUpRequest): Observable<Response<SignUpResponse>>
}