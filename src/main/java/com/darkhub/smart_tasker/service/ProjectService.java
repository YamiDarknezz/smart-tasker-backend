package com.darkhub.smart_tasker.service;

import com.darkhub.smart_tasker.dto.ProjectRequest;
import com.darkhub.smart_tasker.dto.ProjectResponse;
import com.darkhub.smart_tasker.entity.Project;
import com.darkhub.smart_tasker.entity.ProjectMember;
import com.darkhub.smart_tasker.entity.User;
import com.darkhub.smart_tasker.entity.enums.MemberRole;
import com.darkhub.smart_tasker.entity.enums.ProjectStatus;
import com.darkhub.smart_tasker.exception.ExceptionFactory;
import com.darkhub.smart_tasker.repository.ProjectMemberRepository;
import com.darkhub.smart_tasker.repository.ProjectRepository;
import com.darkhub.smart_tasker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository memberRepository;

    public ProjectResponse createProject(ProjectRequest request, String userEmail) {
        User user = getUserByEmail(userEmail);

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .status(ProjectStatus.ACTIVE)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .build();

        projectRepository.save(project);

        ProjectMember member = ProjectMember.builder()
                .project(project)
                .user(user)
                .role(MemberRole.ADMIN)
                .dateJoined(LocalDateTime.now())
                .build();

        memberRepository.save(member);

        return toResponse(project);
    }

    public List<ProjectResponse> getProjectsByUser(String userEmail) {
        User user = getUserByEmail(userEmail);
        List<Project> projects = projectRepository.findByCreatedBy(user);
        return projects.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(UUID id, String userEmail) {
        Project project = findByIdOrThrow(id);
        checkAccess(project, userEmail);
        return toResponse(project);
    }

    public ProjectResponse updateProject(UUID id, ProjectRequest request, String userEmail) {
        Project project = findByIdOrThrow(id);
        checkAccess(project, userEmail);

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setType(request.getType());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());

        projectRepository.save(project);
        return toResponse(project);
    }

    public void deleteProject(UUID id, String userEmail) {
        Project project = findByIdOrThrow(id);
        checkOwnership(project, userEmail);
        projectRepository.delete(project);
    }

    public ProjectResponse changeStatus(UUID id, ProjectStatus status, String userEmail) {
        Project project = findByIdOrThrow(id);
        checkOwnership(project, userEmail);
        project.setStatus(status);
        projectRepository.save(project);
        return toResponse(project);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> ExceptionFactory.userNotFoundByEmail(email));
    }

    private Project findByIdOrThrow(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> ExceptionFactory.projectNotFound(id));
    }

    private void checkAccess(Project project, String email) {
        if (!project.getCreatedBy().getEmail().equals(email)) {
            throw ExceptionFactory.forbidden("You are not a member of this project");
        }
    }

    private void checkOwnership(Project project, String email) {
        if (!project.getCreatedBy().getEmail().equals(email)) {
            throw ExceptionFactory.forbidden("You are not the owner of this project");
        }
    }

    private ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .type(project.getType())
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .createdByEmail(project.getCreatedBy().getEmail())
                .createdAt(project.getCreatedAt())
                .build();
    }
}
