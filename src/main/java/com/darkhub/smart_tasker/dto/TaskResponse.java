package com.darkhub.smart_tasker.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.darkhub.smart_tasker.entity.Task;
import com.darkhub.smart_tasker.entity.enums.Priority;
import com.darkhub.smart_tasker.entity.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority;
    private List<String> tags;
    private Status status;
    private int orderIndex;
    private List<Task.ChecklistItem> checklist;
    private String createdByEmail;
    private LocalDateTime createdAt;
}