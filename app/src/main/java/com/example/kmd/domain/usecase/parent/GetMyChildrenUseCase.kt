package com.example.kmd.domain.usecase.parent

import com.example.kmd.domain.model.Child
import com.example.kmd.domain.repository.IParentRepository
import javax.inject.Inject

class GetMyChildrenUseCase @Inject constructor(
    private val parentRepository: IParentRepository
) {
    suspend operator fun invoke(): Result<List<Child>> {
        return parentRepository.getMyChildren()
    }
}