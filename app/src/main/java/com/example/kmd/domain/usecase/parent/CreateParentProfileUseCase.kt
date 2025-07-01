package com.example.kmd.domain.usecase.parent

import com.example.kmd.domain.model.ParentProfile
import com.example.kmd.domain.repository.IParentRepository
import javax.inject.Inject

class CreateParentProfileUseCase @Inject constructor(
    private val parentRepository: IParentRepository
) {
    suspend operator fun invoke(profile: ParentProfile): Result<ParentProfile> {
        return parentRepository.createParentProfile(profile)
    }
}