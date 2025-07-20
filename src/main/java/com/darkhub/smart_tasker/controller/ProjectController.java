package com.darkhub.smart_tasker.controller;

import com.darkhub.smart_tasker.dto.JSendResponse;
import com.darkhub.smart_tasker.dto.ProjectRequest;
import com.darkhub.smart_tasker.dto.ProjectResponse;
import com.darkhub.smart_tasker.entity.enums.ProjectStatus;
import com.darkhub.smart_tasker.service.ProjectService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // ✅ Crear nuevo proyecto
    @PostMapping
    public ResponseEntity<JSendResponse<ProjectResponse>> createProject(
            @RequestBody ProjectRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        ProjectResponse response = projectService.createProject(request, email);
        return ResponseEntity.ok(JSendResponse.success(response));
    }

    // ✅ Obtener todos los proyectos del usuario autenticado
    @GetMapping
    public ResponseEntity<JSendResponse<List<ProjectResponse>>> getUserProjects(
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        List<ProjectResponse> responses = projectService.getProjectsByUser(email);
        return ResponseEntity.ok(JSendResponse.success(responses));
    }

    // ✅ Obtener un proyecto específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<JSendResponse<ProjectResponse>> getProjectById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        ProjectResponse response = projectService.getProjectById(id, email);
        return ResponseEntity.ok(JSendResponse.success(response));
    }

    // ✅ Actualizar un proyecto
    @PutMapping("/{id}")
    public ResponseEntity<JSendResponse<ProjectResponse>> updateProject(
            @PathVariable UUID id,
            @RequestBody ProjectRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        ProjectResponse response = projectService.updateProject(id, request, email);
        return ResponseEntity.ok(JSendResponse.success(response));
    }

    // ✅ Eliminar un proyecto (no se retorna data, solo mensaje opcional si deseas)
    @DeleteMapping("/{id}")
    public ResponseEntity<JSendResponse<Void>> deleteProject(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        projectService.deleteProject(id, email);
        return ResponseEntity.ok(JSendResponse.success(null)); // O puedes incluir un mensaje si quieres
    }

    // ✅ Cambiar estado del proyecto
    @PatchMapping("/{id}/status")
    public ResponseEntity<JSendResponse<ProjectResponse>> changeProjectStatus(
            @PathVariable UUID id,
            @RequestParam ProjectStatus status,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        ProjectResponse response = projectService.changeStatus(id, status, email);
        return ResponseEntity.ok(JSendResponse.success(response));
    }
}
