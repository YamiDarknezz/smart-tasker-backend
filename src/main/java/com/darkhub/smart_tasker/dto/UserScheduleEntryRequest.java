package com.darkhub.smart_tasker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserScheduleEntryRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID projectId;

    // Uno de estos dos debe ser no nulo (pero no ambos)
    private Integer dayOfWeek;

    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotBlank
    private String name;

    private String location;

    private String color;

    private Boolean isMandatory;

    private String notes;

    @Min(0)
    private Integer reminderMinutesBefore;
}
