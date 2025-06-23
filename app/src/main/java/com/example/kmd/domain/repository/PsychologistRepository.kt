package com.example.kmd.domain.repository

import android.util.Log
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
        return try {
            val response = api.getPsychologists()
            val domainList = response.results.map { it.toDomain() }
            Log.d("Repo", "Mapped ${domainList.size} psychologists")
            return domainList
        } catch (e: Exception) {
            Log.e("Repo", "API call failed", e)
            throw e
        }
    }


    override suspend fun getPsychologistDetail(id: String): PsychologistDetail {
        val response = api.getPsychologistDetail(id)
        return response.toDomain()
    }
}

