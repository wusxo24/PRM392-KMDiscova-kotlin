package com.example.kmd.data.mapper

import com.example.kmd.data.remote.dto.chat.ChatMessageDto
import com.example.kmd.data.remote.dto.chat.ChatRoomDto
import com.example.kmd.data.remote.dto.chat.ChatResponseDto
import com.example.kmd.data.remote.dto.chat.ChatRoomsResponseDto
import com.example.kmd.domain.model.ChatMessage
import com.example.kmd.domain.model.ChatRoom
import com.example.kmd.domain.model.ChatResponse
import com.example.kmd.domain.model.MessageSenderType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ChatMapper {
    
    fun ChatMessageDto.toDomain(): ChatMessage {
        return ChatMessage(
            id = id,
            chatRoomId = chatRoomId,
            senderId = senderId,
            senderName = senderName,
            senderType = when (senderType.lowercase()) {
                "parent" -> MessageSenderType.PARENT
                "psychologist" -> MessageSenderType.PSYCHOLOGIST
                else -> MessageSenderType.PARENT
            },
            content = content,
            timestamp = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME),
            isRead = isRead
        )
    }
    
    fun ChatRoomDto.toDomain(): ChatRoom {
        return ChatRoom(
            id = id,
            parentId = parentId,
            psychologistId = psychologistId,
            parentName = parentName,
            psychologistName = psychologistName,
            lastMessage = lastMessage,
            lastMessageTime = lastMessageTime?.let { 
                LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME) 
            },
            unreadCount = unreadCount,
            createdAt = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME)
        )
    }
    
    fun ChatResponseDto.toDomain(): ChatResponse {
        return ChatResponse(
            message = message,
            chatRoom = chatRoom?.toDomain(),
            messages = messages?.map { it.toDomain() }
        )
    }
    
    fun ChatRoomsResponseDto.toDomain(): List<ChatRoom> {
        return chatRooms.map { it.toDomain() }
    }
} 