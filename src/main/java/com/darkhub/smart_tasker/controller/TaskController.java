package com.darkhub.smart_tasker.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.darkhub.smart_tasker.dto.JSendResponse;
import com.darkhub.smart_tasker.dto.TaskRequest;
import com.darkhub.smart_tasker.dto.TaskResponse;
import com.darkhub.smart_tasker.entity.enums.Status;
import com.darkhub.smart_tasker.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<JSendResponse<List<TaskResponse>>> getTasks(
            @PathVariable UUID projectId,
            @RequestParam String requesterEmail
    ) {
        return ResponseEntity.ok(JSendResponse.success(taskService.getTasks(projectId, requesterEmail)));
    }

    @PostMapping
    public ResponseEntity<JSendResponse<TaskResponse>> createTask(
            @PathVariable UUID projectId,
            @RequestBody TaskRequest request,
            @RequestParam String requesterEmail
    ) {
        return ResponseEntity.ok(JSendResponse.success(taskService.createTask(projectId, request, requesterEmail)));
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<JSendResponse<TaskResponse>> updateStatus(
            @PathVariable UUID taskId,
            @RequestParam Status status
    ) {
        return ResponseEntity.ok(JSendResponse.success(taskService.updateStatus(taskId, status)));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<JSendResponse<Void>> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(JSendResponse.success(null));
    }
}
