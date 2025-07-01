package com.example.kmd.domain.repository

import com.example.kmd.domain.model.Child
import com.example.kmd.domain.model.ParentProfile

interface IParentRepository {
    suspend fun createParentProfile(profile: ParentProfile): Result<ParentProfile>
    suspend fun getParentProfile(): Result<ParentProfile>
    suspend fun updateParentProfile(profile: ParentProfile): Result<ParentProfile>
    suspend fun addChild(child: Child): Result<Child>
    suspend fun getMyChildren(): Result<List<Child>>
}