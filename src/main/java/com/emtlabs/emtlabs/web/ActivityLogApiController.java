package com.emtlabs.emtlabs.web;

import com.emtlabs.emtlabs.model.dto.ActivityLogDto;
import com.emtlabs.emtlabs.service.ActivityLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Activity Logs", description = "Activity records created by event listeners")
@RestController
@RequestMapping("/api/activity-logs")
@RequiredArgsConstructor
public class ActivityLogApiController {

    private static final int DEFAULT_PAGE = 0;
    private static final int MAX_SIZE = 100;

    private final ActivityLogService activityLogService;

    @Operation(summary = "List activity logs with pagination")
    @GetMapping
    public Page<ActivityLogDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        int safePage = Math.max(page, DEFAULT_PAGE);
        int safeSize = Math.min(Math.max(size, 1), MAX_SIZE);
        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by(direction, "eventTimestamp"));
        return activityLogService.findAll(pageable);
    }
}

