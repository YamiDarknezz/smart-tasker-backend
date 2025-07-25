package com.darkhub.smart_tasker.dto;

import com.darkhub.smart_tasker.entity.enums.ProjectType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectRequest {
    private String name;
    private String description;
    private ProjectType type;
    private LocalDate startDate;
    private LocalDate endDate;
}
