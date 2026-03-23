package com.emtlabs.emtlabs.service.event;

public record BookRentedEvent(
        Long bookId,
        String bookName,
        Integer remainingCopies
) {
}

