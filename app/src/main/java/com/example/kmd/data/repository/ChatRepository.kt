package com.example.kmd.data.repository

import com.example.kmd.data.mapper.ChatMapper.toDomain
import com.example.kmd.data.remote.api.ChatApiService
import com.example.kmd.data.remote.dto.chat.SendMessageRequestDto
import com.example.kmd.domain.model.ChatMessage
import com.example.kmd.domain.model.ChatRoom
import com.example.kmd.domain.model.ChatResponse
import com.example.kmd.domain.model.SendMessageRequest
import com.example.kmd.domain.repository.IChatRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val api: ChatApiService
) : IChatRepository {

    // Mock data for offline functionality - made persistent with @Singleton
    private val mockChatRooms = mutableListOf<ChatRoom>()
    private val mockMessages = mutableMapOf<String, MutableList<ChatMessage>>()

    override suspend fun getChatRooms(): List<ChatRoom> {
        return try {
            // Try API first, fallback to mock data
            val response = api.getChatRooms()
            response.toDomain()
        } catch (e: Exception) {
            // Return mock data if API fails
            mockChatRooms.toList()
        }
    }

    override suspend fun getChatMessages(roomId: String): List<ChatMessage> {
        return try {
            // Try API first, fallback to mock data
            val response = api.getChatMessages(roomId)
            response.map { it.toDomain() }
        } catch (e: Exception) {
            // Return mock data if API fails
            mockMessages[roomId]?.toList() ?: emptyList()
        }
    }

    override suspend fun sendMessage(request: SendMessageRequest): ChatResponse {
        return try {
            // Try API first, fallback to mock data
            val dto = SendMessageRequestDto(
                chatRoomId = request.chatRoomId,
                content = request.content
            )
            val response = api.sendMessage(dto)
            response.toDomain()
        } catch (e: Exception) {
            // Create mock response
            val message = ChatMessage(
                id = UUID.randomUUID().toString(),
                chatRoomId = request.chatRoomId,
                senderId = "parent",
                senderName = "You",
                senderType = com.example.kmd.domain.model.MessageSenderType.PARENT,
                content = request.content,
                timestamp = LocalDateTime.now(),
                isRead = true
            )
            
            // Add message to mock storage
            if (!mockMessages.containsKey(request.chatRoomId)) {
                mockMessages[request.chatRoomId] = mutableListOf()
            }
            mockMessages[request.chatRoomId]?.add(message)
            
            ChatResponse(
                message = "Message sent successfully",
                chatRoom = null,
                messages = mockMessages[request.chatRoomId]?.toList() ?: listOf(message)
            )
        }
    }

    override suspend fun createChatRoom(psychologistId: String): ChatResponse {
        return try {
            // Try API first, fallback to mock data
            val response = api.createChatRoom(psychologistId)
            response.toDomain()
        } catch (e: Exception) {
            // Check if chat room already exists
            val existingRoom = mockChatRooms.find { it.psychologistId == psychologistId }
            if (existingRoom != null) {
                return ChatResponse(
                    message = "Chat room already exists",
                    chatRoom = existingRoom,
                    messages = mockMessages[existingRoom.id] ?: emptyList()
                )
            }
            
            // Create mock chat room
            val chatRoom = ChatRoom(
                id = psychologistId, // Use psychologist ID as room ID for simplicity
                parentId = "parent",
                psychologistId = psychologistId,
                parentName = "You",
                psychologistName = "Dr. Smith", // Mock name
                lastMessage = null,
                lastMessageTime = null,
                unreadCount = 0,
                createdAt = LocalDateTime.now()
            )
            
            mockChatRooms.add(chatRoom)
            
            ChatResponse(
                message = "Chat room created successfully",
                chatRoom = chatRoom,
                messages = emptyList()
            )
        }
    }

    override suspend fun markMessagesAsRead(roomId: String): ChatResponse {
        return try {
            // Try API first, fallback to mock data
            val response = api.markMessagesAsRead(roomId)
            response.toDomain()
        } catch (e: Exception) {
            // Mark messages as read in mock storage
            mockMessages[roomId]?.forEach { it.copy(isRead = true) }
            
            ChatResponse(
                message = "Messages marked as read",
                chatRoom = null,
                messages = null
            )
        }
    }

    // Helper method to add auto-reply message to storage
    fun addAutoReplyMessage(chatRoomId: String, autoReplyMessage: ChatMessage) {
        if (!mockMessages.containsKey(chatRoomId)) {
            mockMessages[chatRoomId] = mutableListOf()
        }
        mockMessages[chatRoomId]?.add(autoReplyMessage)
    }
} 