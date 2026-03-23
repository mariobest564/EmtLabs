package com.emtlabs.emtlabs.service;

import com.emtlabs.emtlabs.model.dto.ActivityLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityLogService {

    Page<ActivityLogDto> findAll(Pageable pageable);
}

