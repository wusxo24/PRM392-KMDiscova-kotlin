package com.example.kmd.domain.usecase.chat

import com.example.kmd.domain.model.ChatRoom
import com.example.kmd.domain.repository.IChatRepository
import javax.inject.Inject

class GetChatRoomsUseCase @Inject constructor(
    private val repository: IChatRepository
) {
    suspend operator fun invoke(): List<ChatRoom> {
        return repository.getChatRooms()
    }
} 