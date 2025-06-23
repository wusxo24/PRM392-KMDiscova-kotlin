

package com.example.kmd.domain.repository
import com.example.kmd.data.remote.dto.psychologist.PsychologistListResponse
import com.example.kmd.domain.model.Psychologist
import com.example.kmd.domain.model.PsychologistDetail

interface IPsychologistRepository {
    suspend fun getPsychologists(): List<Psychologist>
    suspend fun getPsychologistDetail(id: String): PsychologistDetail
}
