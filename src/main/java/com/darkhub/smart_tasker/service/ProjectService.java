package com.darkhub.smart_tasker.service;

import com.darkhub.smart_tasker.dto.ProjectRequest;
import com.darkhub.smart_tasker.dto.ProjectResponse;
import com.darkhub.smart_tasker.entity.Project;
import com.darkhub.smart_tasker.entity.User;
import com.darkhub.smart_tasker.entity.enums.ProjectStatus;
import com.darkhub.smart_tasker.exception.ExceptionFactory;
import com.darkhub.smart_tasker.repository.ProjectRepository;
import com.darkhub.smart_tasker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectResponse createProject(ProjectRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) throw ExceptionFactory.userNotFoundByEmail(userEmail);

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

        return toResponse(project);
    }

    public List<ProjectResponse> getProjectsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) throw ExceptionFactory.userNotFoundByEmail(userEmail);

        List<Project> projects = projectRepository.findByCreatedBy(user);
        return projects.stream().map(this::toResponse).collect(Collectors.toList());
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
