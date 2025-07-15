package com.darkhub.smart_tasker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.darkhub.smart_tasker.entity.enums.ProjectType;
import com.darkhub.smart_tasker.entity.enums.ProjectStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectType type;

    @ManyToOne
    private User createdBy;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private LocalDateTime createdAt;
}
