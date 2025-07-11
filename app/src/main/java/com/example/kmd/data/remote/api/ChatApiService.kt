package com.example.kmd.data.remote.api

import com.example.kmd.data.remote.dto.chat.ChatMessageDto
import com.example.kmd.data.remote.dto.chat.ChatResponseDto
import com.example.kmd.data.remote.dto.chat.ChatRoomsResponseDto
import com.example.kmd.data.remote.dto.chat.SendMessageRequestDto
import retrofit2.http.*

interface ChatApiService {
    
    @GET("/api/chat/rooms/")
    suspend fun getChatRooms(): ChatRoomsResponseDto
    
    @GET("/api/chat/rooms/{roomId}/messages/")
    suspend fun getChatMessages(@Path("roomId") roomId: String): List<ChatMessageDto>
    
    @POST("/api/chat/send-message/")
    suspend fun sendMessage(@Body request: SendMessageRequestDto): ChatResponseDto
    
    @POST("/api/chat/rooms/{psychologistId}/create/")
    suspend fun createChatRoom(@Path("psychologistId") psychologistId: String): ChatResponseDto
    
    @PUT("/api/chat/rooms/{roomId}/mark-read/")
    suspend fun markMessagesAsRead(@Path("roomId") roomId: String): ChatResponseDto
} 