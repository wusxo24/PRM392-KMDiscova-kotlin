package com.example.kmd.data.remote.api

import com.example.kmd.data.remote.dto.auth.LoginRequest
import com.example.kmd.data.remote.dto.auth.LoginResponse
import com.example.kmd.data.remote.dto.auth.RegisterRequest
import com.example.kmd.data.remote.dto.auth.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/auth/login/")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/auth/register/")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

//    @POST("/api/auth/logout/")
//    suspend fun logout(): ApiResponse<Unit>
}
