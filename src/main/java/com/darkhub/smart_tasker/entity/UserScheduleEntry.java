package com.darkhub.smart_tasker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserScheduleEntry {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    private Integer dayOfWeek; // 0–6, opcional

    private LocalDate date; // opcional

    private LocalTime startTime;

    private LocalTime endTime;

    private String name;

    private String location;

    private String color;

    private Boolean isMandatory;

    private String notes;

    private Integer reminderMinutesBefore; // opcional
}
