package com.example.kmd.domain.usecase.psychologist

import com.example.kmd.domain.model.Psychologist
import com.example.kmd.domain.repository.IPsychologistRepository
import javax.inject.Inject

class GetPsychologistsUseCase @Inject constructor(
    private val repository: IPsychologistRepository
) {
    suspend operator fun invoke(): List<Psychologist> {
        return repository.getPsychologists()
    }
}
