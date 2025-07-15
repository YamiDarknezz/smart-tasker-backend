package com.darkhub.smart_tasker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.darkhub.smart_tasker.entity.enums.Priority;
import com.darkhub.smart_tasker.entity.enums.Status;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Project project;

    private String title;

    private String description;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ElementCollection
    private List<String> tags;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer orderIndex;

    @ElementCollection
    private List<ChecklistItem> checklist;

    @ManyToOne
    private User createdBy;

    private LocalDateTime createdAt;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChecklistItem {
        private String title;
        private boolean isDone;
        private int orderIndex;
    }
}
