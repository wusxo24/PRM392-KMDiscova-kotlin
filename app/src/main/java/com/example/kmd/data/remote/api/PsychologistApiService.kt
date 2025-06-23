package com.example.kmd.data.remote.api

import com.example.kmd.data.remote.dto.psychologist.PsychologistDto
import com.example.kmd.data.remote.dto.psychologist.PsychologistDetailDto
import com.example.kmd.data.remote.dto.psychologist.PsychologistListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PsychologistApiService {

    @GET("/api/psychologists/marketplace/")
    suspend fun getPsychologists(): PsychologistListResponse

    @GET("/api/psychologists/marketplace/{id}/")
    suspend fun getPsychologistDetail(@Path("id") id: String): PsychologistDetailDto


}
