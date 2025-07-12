package com.example.kmd.data.repository

import com.example.kmd.data.mapper.toDomain
import com.example.kmd.data.remote.api.ParentApiService
import com.example.kmd.data.remote.dto.child.AddChildRequest
import com.example.kmd.data.remote.dto.child.CreateChildResponse
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

    override suspend fun updateParentProfile(profile: ParentProfile): Result<ParentProfile> {
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
            val response = parentApiService.updateParentProfile(request)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addChild(child: Child): Result<Child> {
        return try {
            val request = AddChildRequest(
                first_name = child.firstName,
                date_of_birth = child.dateOfBirth,
                last_name = child.lastName.takeIf { it.isNotEmpty() },
                nickname = null, // We can add this later if needed
                gender = child.gender, // Now required
                health_status = null, // We can add these fields later if needed
                developmental_concerns = null,
                parental_goals = null
            )
            val response = parentApiService.addChild(request)
            Result.success(response.child.toDomain())
        } catch (e: Exception) {
            println("API Error: ${e.message}")
            println("Exception type: ${e.javaClass.simpleName}")
            e.printStackTrace()
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