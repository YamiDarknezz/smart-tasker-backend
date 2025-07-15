package com.darkhub.smart_tasker.dto;

import com.darkhub.smart_tasker.entity.enums.ProjectType;
import com.darkhub.smart_tasker.entity.enums.ProjectStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ProjectResponse {
    private UUID id;
    private String name;
    private String description;
    private ProjectType type;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String createdByEmail;
    private LocalDateTime createdAt;
}
