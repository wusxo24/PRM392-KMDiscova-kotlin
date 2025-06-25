package com.example.kmd.data.mapper

import com.example.kmd.data.remote.dto.psychologist.AvailableSlotsResponseDto
import com.example.kmd.data.remote.dto.psychologist.SlotDto
import com.example.kmd.domain.model.AvailableSlotsResponse
import com.example.kmd.domain.model.Slot

fun SlotDto.toDomain(): Slot {
    return Slot(
        id = slot_id,
        date = date,
        startTime = start_time,
        endTime = end_time,
        sessionTypes = session_types,
        isConsecutiveBlock = is_consecutive_block
    )
}

fun AvailableSlotsResponseDto.toDomain(): AvailableSlotsResponse {
    return AvailableSlotsResponse(
        psychologistName = psychologist_name,
        sessionType = session_type,
        totalSlots = total_slots,
        availableSlots = available_slots.map { it.toDomain() }
    )
}
