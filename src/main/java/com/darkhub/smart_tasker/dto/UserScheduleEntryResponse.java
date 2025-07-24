package com.darkhub.smart_tasker.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserScheduleEntryResponse {

    private UUID id;

    private UUID userId;

    private UUID projectId;

    private Integer dayOfWeek;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String name;

    private String location;

    private String color;

    private Boolean isMandatory;

    private String notes;

    private Integer reminderMinutesBefore;
}
