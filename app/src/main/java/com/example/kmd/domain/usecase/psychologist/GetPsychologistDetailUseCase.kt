package com.example.kmd.domain.usecase.psychologist

import com.example.kmd.domain.model.PsychologistDetail
import com.example.kmd.domain.repository.IPsychologistRepository
import javax.inject.Inject

class GetPsychologistDetailUseCase @Inject constructor(
    private val repository: IPsychologistRepository
) {
    suspend operator fun invoke(id: String): PsychologistDetail {
        return repository.getPsychologistDetail(id)
    }
}
