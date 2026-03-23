package com.emtlabs.emtlabs.service.impl;

import com.emtlabs.emtlabs.model.dto.ActivityLogDto;
import com.emtlabs.emtlabs.repository.ActivityLogRepository;
import com.emtlabs.emtlabs.service.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    @Override
    public Page<ActivityLogDto> findAll(Pageable pageable) {
        return activityLogRepository.findAll(pageable)
                .map(log -> new ActivityLogDto(
                        log.getId(),
                        log.getBookName(),
                        log.getEventTimestamp(),
                        log.getEventType()
                ));
    }
}

