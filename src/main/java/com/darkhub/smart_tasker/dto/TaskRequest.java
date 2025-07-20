package com.darkhub.smart_tasker.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.darkhub.smart_tasker.entity.Task;
import com.darkhub.smart_tasker.entity.enums.Priority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequest {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority;
    private List<String> tags;
    private List<Task.ChecklistItem> checklist;
}