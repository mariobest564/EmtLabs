package com.emtlabs.emtlabs.model.dto;

import com.emtlabs.emtlabs.model.ActivityEventType;
import java.time.LocalDateTime;

public record ActivityLogDto(
        Long id,
        String bookName,
        LocalDateTime eventTimestamp,
        ActivityEventType eventType
) {
}

