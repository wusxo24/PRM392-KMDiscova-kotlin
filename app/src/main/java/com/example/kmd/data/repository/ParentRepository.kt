package com.example.kmd.data.repository

import com.example.kmd.data.mapper.toDomain
import com.example.kmd.data.remote.api.ParentApiService
import com.example.kmd.data.remote.dto.child.AddChildRequest
import com.example.kmd.data.remote.dto.parent.CreateParentProfileRequest
import com.example.kmd.domain.model.Child
import com.example.kmd.domain.model.ParentProfile
import com.example.kmd.domain.repository.IParentRepository
import javax.inject.Inject

class ParentRepository @Inject constructor(
    private val parentApiService: ParentApiService
) : IParentRepository {
    override suspend fun createParentProfile(profile: ParentProfile): Result<ParentProfile> {
        return try {
            val request = CreateParentProfileRequest(
                first_name = profile.firstName,
                last_name = profile.lastName,
                phone_number = profile.phoneNumber,
                address_line1 = profile.addressLine1,
                city = profile.city,
                state_province = profile.stateProvince,
                postal_code = profile.postalCode,
                country = profile.country
            )
            val response = parentApiService.createParentProfile(request)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getParentProfile(): Result<ParentProfile> {
        return try {
            val response = parentApiService.getParentProfile()
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun addChild(child: Child): Result<Child> {
        return try {
            val request = AddChildRequest(
                first_name = child.firstName,
                last_name = child.lastName,
                nickname = child.displayName, // Or add a nickname field to your domain model
                date_of_birth = child.dateOfBirth,
                gender = child.gender,
                health_status = "", // Add these fields to your domain model and UI
                developmental_concerns = "",
                parental_goals = ""
            )
            val response = parentApiService.addChild(request)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMyChildren(): Result<List<Child>> {
        return try {
            val response = parentApiService.getMyChildren()
            Result.success(response.children.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}