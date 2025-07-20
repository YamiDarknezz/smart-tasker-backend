package com.darkhub.smart_tasker.service;

import com.darkhub.smart_tasker.dto.TaskRequest;
import com.darkhub.smart_tasker.dto.TaskResponse;
import com.darkhub.smart_tasker.entity.Project;
import com.darkhub.smart_tasker.entity.Task;
import com.darkhub.smart_tasker.entity.User;
import com.darkhub.smart_tasker.entity.enums.Status;
import com.darkhub.smart_tasker.exception.ExceptionFactory;
import com.darkhub.smart_tasker.repository.ProjectRepository;
import com.darkhub.smart_tasker.repository.TaskRepository;
import com.darkhub.smart_tasker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(UUID projectId, TaskRequest request, String userEmail) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> ExceptionFactory.projectNotFound(projectId));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> ExceptionFactory.userNotFoundByEmail(userEmail));

        Task task = Task.builder()
                .project(project)
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .priority(request.getPriority())
                .tags(request.getTags())
                .checklist(request.getChecklist())
                .status(Status.TODO)
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .orderIndex(0)
                .build();

        taskRepository.save(task);
        return toResponse(task);
    }

    public List<TaskResponse> getTasks(UUID projectId, String userEmail) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream().map(this::toResponse).toList();
    }

    public TaskResponse updateStatus(UUID taskId, Status status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> ExceptionFactory.taskNotFound(taskId));
        task.setStatus(status);
        taskRepository.save(task);
        return toResponse(task);
    }

    public void deleteTask(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> ExceptionFactory.taskNotFound(taskId));
        taskRepository.delete(task);
    }

    private TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .tags(task.getTags())
                .status(task.getStatus())
                .orderIndex(task.getOrderIndex())
                .checklist(task.getChecklist())
                .createdByEmail(task.getCreatedBy().getEmail())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
