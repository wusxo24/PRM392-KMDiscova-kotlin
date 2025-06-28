package com.example.kmd.domain.usecase.parent

import com.example.kmd.domain.model.Child
import com.example.kmd.domain.repository.IParentRepository
import javax.inject.Inject

class AddChildUseCase @Inject constructor(
    private val parentRepository: IParentRepository
) {
    suspend operator fun invoke(child: Child): Result<Child> {
        return parentRepository.addChild(child)
    }
}