package com.example.kmd.data.remote.api

import com.example.kmd.data.remote.dto.auth.LoginRequest
import com.example.kmd.data.remote.dto.auth.LoginResponse
import com.example.kmd.data.remote.dto.auth.RegisterRequest
import com.example.kmd.data.remote.dto.auth.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("https://kmdiscova.id.vn/api/auth/login/")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("https://kmdiscova.id.vn/api/auth/register/")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse
}
