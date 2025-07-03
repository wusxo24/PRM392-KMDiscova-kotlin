package com.example.kmd.domain.repository

import com.example.kmd.domain.model.ChatMessage
import com.example.kmd.domain.model.ChatRoom
import com.example.kmd.domain.model.ChatResponse
import com.example.kmd.domain.model.SendMessageRequest

interface IChatRepository {
    suspend fun getChatRooms(): List<ChatRoom>
    suspend fun getChatMessages(roomId: String): List<ChatMessage>
    suspend fun sendMessage(request: SendMessageRequest): ChatResponse
    suspend fun createChatRoom(psychologistId: String): ChatResponse
    suspend fun markMessagesAsRead(roomId: String): ChatResponse
} 