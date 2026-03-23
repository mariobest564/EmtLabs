package com.emtlabs.emtlabs.service.impl;

import com.emtlabs.emtlabs.model.ActivityEventType;
import com.emtlabs.emtlabs.model.ActivityLog;
import com.emtlabs.emtlabs.repository.ActivityLogRepository;
import com.emtlabs.emtlabs.service.event.BookUnavailableEvent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookUnavailableEventListener {

    private final ActivityLogRepository activityLogRepository;

    @EventListener
    public void onBookUnavailable(BookUnavailableEvent event) {
        log.warn("Book became unavailable: id={}, name='{}'", event.bookId(), event.bookName());

        ActivityLog logEntry = new ActivityLog();
        logEntry.setBookName(event.bookName());
        logEntry.setEventTimestamp(LocalDateTime.now());
        logEntry.setEventType(ActivityEventType.BOOK_UNAVAILABLE);
        activityLogRepository.save(logEntry);
    }
}

