package com.emtlabs.emtlabs.service.impl;

import com.emtlabs.emtlabs.model.ActivityEventType;
import com.emtlabs.emtlabs.model.ActivityLog;
import com.emtlabs.emtlabs.repository.ActivityLogRepository;
import com.emtlabs.emtlabs.service.event.BookRentedEvent;
import com.emtlabs.emtlabs.service.event.BookUnavailableEvent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookRentalEventListener {

    private final ActivityLogRepository activityLogRepository;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void onBookRented(BookRentedEvent event) {
        log.info("Book rented: id={}, name='{}', remainingCopies={}",
                event.bookId(), event.bookName(), event.remainingCopies());

        ActivityLog logEntry = new ActivityLog();
        logEntry.setBookName(event.bookName());
        logEntry.setEventTimestamp(LocalDateTime.now());
        logEntry.setEventType(ActivityEventType.BOOK_RENTED);
        activityLogRepository.save(logEntry);

        if (event.remainingCopies() == 0) {
            eventPublisher.publishEvent(new BookUnavailableEvent(event.bookId(), event.bookName()));
        }
    }
}

