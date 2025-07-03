package com.example.kmd.data.remote.dto.chat

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ChatMessageDto(
    val id: String,
    @SerializedName("chat_room_id")
    val chatRoomId: String,
    @SerializedName("sender_id")
    val senderId: String,
    @SerializedName("sender_name")
    val senderName: String,
    @SerializedName("sender_type")
    val senderType: String,
    val content: String,
    val timestamp: String,
    @SerializedName("is_read")
    val isRead: Boolean = false
)

data class ChatRoomDto(
    val id: String,
    @SerializedName("parent_id")
    val parentId: String,
    @SerializedName("psychologist_id")
    val psychologistId: String,
    @SerializedName("parent_name")
    val parentName: String,
    @SerializedName("psychologist_name")
    val psychologistName: String,
    @SerializedName("last_message")
    val lastMessage: String?,
    @SerializedName("last_message_time")
    val lastMessageTime: String?,
    @SerializedName("unread_count")
    val unreadCount: Int = 0,
    @SerializedName("created_at")
    val createdAt: String
)

data class SendMessageRequestDto(
    @SerializedName("chat_room_id")
    val chatRoomId: String,
    val content: String
)

data class ChatResponseDto(
    val message: String,
    @SerializedName("chat_room")
    val chatRoom: ChatRoomDto? = null,
    val messages: List<ChatMessageDto>? = null
)

data class ChatRoomsResponseDto(
    val count: Int,
    @SerializedName("chat_rooms")
    val chatRooms: List<ChatRoomDto>
) 