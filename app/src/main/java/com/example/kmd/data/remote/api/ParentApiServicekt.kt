package com.example.kmd.data.remote.api

import com.example.kmd.data.remote.dto.child.AddChildRequest
import com.example.kmd.data.remote.dto.child.ChildDto
import com.example.kmd.data.remote.dto.child.MyChildrenResponse
import com.example.kmd.data.remote.dto.parent.CreateParentProfileRequest
import com.example.kmd.data.remote.dto.parent.ParentProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ParentApiService {
    @PATCH("/api/parents/profile/update_profile/")
    suspend fun createParentProfile(@Body request: CreateParentProfileRequest): ParentProfileDto

    @GET("/api/parents/profile/profile/") // Add this
    suspend fun getParentProfile(): ParentProfileDto

    @POST("/api/children/profile/")
    suspend fun addChild(@Body request: AddChildRequest): ChildDto

    @GET("/api/children/profile/my_children/")
    suspend fun getMyChildren(): MyChildrenResponse

    @PATCH("/api/parents/profile/update_profile/")
    suspend fun updateParentProfile(@Body request: CreateParentProfileRequest): ParentProfileDto

}