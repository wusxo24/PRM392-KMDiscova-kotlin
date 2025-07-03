package com.example.kmd.domain.model

import java.time.LocalDateTime

data class ChatMessage(
    val id: String,
    val chatRoomId: String,
    val senderId: String,
    val senderName: String,
    val senderType: MessageSenderType,
    val content: String,
    val timestamp: LocalDateTime,
    val isRead: Boolean = false
)

data class ChatRoom(
    val id: String,
    val parentId: String,
    val psychologistId: String,
    val parentName: String,
    val psychologistName: String,
    val lastMessage: String?,
    val lastMessageTime: LocalDateTime?,
    val unreadCount: Int = 0,
    val createdAt: LocalDateTime
)

enum class MessageSenderType {
    PARENT,
    PSYCHOLOGIST
}

data class SendMessageRequest(
    val chatRoomId: String,
    val content: String
)

data class ChatResponse(
    val message: String,
    val chatRoom: ChatRoom? = null,
    val messages: List<ChatMessage>? = null
) 