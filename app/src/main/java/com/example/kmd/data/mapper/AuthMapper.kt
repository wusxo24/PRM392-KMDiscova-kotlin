package com.example.kmd.data.mapper

import com.example.kmd.data.remote.dto.auth.LoginResponse
import com.example.kmd.data.remote.dto.auth.RegisterResponse
import com.example.kmd.domain.model.User
import javax.inject.Inject

class   AuthMapper @Inject constructor(){
    fun mapLoginResponseToUser(response: LoginResponse): User {
        return User(response.userId, response.email, response.token)
    }

    fun mapRegisterResponseToUser(response: RegisterResponse): User {
        return User(response.userId, response.email, response.token)
    }
}
