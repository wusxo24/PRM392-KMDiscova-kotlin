package com.example.kmd.domain.repository

import com.example.kmd.data.mapper.toDomain
import com.example.kmd.data.remote.api.PsychologistApiService
import com.example.kmd.domain.model.Psychologist
import com.example.kmd.domain.model.PsychologistDetail
import com.example.kmd.domain.repository.IPsychologistRepository
import javax.inject.Inject

class PsychologistRepository @Inject constructor(
    private val api: PsychologistApiService
) : IPsychologistRepository {

    override suspend fun getPsychologists(): List<Psychologist> {
        val response = api.getPsychologists()
        return response.results.map { it.toDomain() }
    }

    override suspend fun getPsychologistDetail(id: String): PsychologistDetail {
        val response = api.getPsychologistDetail(id)
        return response.toDomain()
    }
}

