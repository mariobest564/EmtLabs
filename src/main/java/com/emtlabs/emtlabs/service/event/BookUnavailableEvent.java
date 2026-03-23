package com.emtlabs.emtlabs.service.event;

public record BookUnavailableEvent(
        Long bookId,
        String bookName
) {
}

