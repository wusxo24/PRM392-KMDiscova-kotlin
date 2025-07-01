package com.example.kmd.domain.usecase.parent

import com.example.kmd.domain.model.ParentProfile
import com.example.kmd.domain.repository.IParentRepository
import javax.inject.Inject

class GetParentProfileUseCase @Inject constructor(
    private val parentRepository: IParentRepository
) {
    suspend operator fun invoke(): Result<ParentProfile> {
        return parentRepository.getParentProfile()
    }
}