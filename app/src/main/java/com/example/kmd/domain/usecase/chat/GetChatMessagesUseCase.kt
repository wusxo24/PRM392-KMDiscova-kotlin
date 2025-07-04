package com.example.kmd.domain.usecase.chat

import com.example.kmd.domain.model.ChatMessage
import com.example.kmd.domain.repository.IChatRepository
import javax.inject.Inject

class GetChatMessagesUseCase @Inject constructor(
    private val repository: IChatRepository
) {
    suspend operator fun invoke(roomId: String): List<ChatMessage> {
        return repository.getChatMessages(roomId)
    }
} 