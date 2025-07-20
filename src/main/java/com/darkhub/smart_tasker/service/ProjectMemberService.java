package com.darkhub.smart_tasker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import com.darkhub.smart_tasker.exception.ExceptionFactory;

import com.darkhub.smart_tasker.dto.ProjectMemberRequest;
import com.darkhub.smart_tasker.dto.ProjectMemberResponse;
import com.darkhub.smart_tasker.entity.Project;
import com.darkhub.smart_tasker.entity.ProjectMember;
import com.darkhub.smart_tasker.entity.User;
import com.darkhub.smart_tasker.entity.enums.MemberRole;
import com.darkhub.smart_tasker.repository.ProjectMemberRepository;
import com.darkhub.smart_tasker.repository.ProjectRepository;
import com.darkhub.smart_tasker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository memberRepository;

    public List<ProjectMemberResponse> getMembers(UUID projectId, String requesterEmail) {
        Project project = findProjectOrThrow(projectId);
        checkAccess(project, requesterEmail);

        return memberRepository.findByProject(project).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProjectMemberResponse addMember(UUID projectId, ProjectMemberRequest request, String requesterEmail) {
        Project project = findProjectOrThrow(projectId);
        checkOwnership(project, requesterEmail);

        User userToAdd = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> ExceptionFactory.userNotFoundByEmail(request.getEmail()));

        boolean alreadyExists = memberRepository.existsByProjectAndUser(project, userToAdd);
        if (alreadyExists) throw ExceptionFactory.conflict("User is already a member");

        ProjectMember member = ProjectMember.builder()
                .project(project)
                .user(userToAdd)
                .role(request.getRole())
                .dateJoined(LocalDateTime.now())
                .build();

        memberRepository.save(member);
        return toResponse(member);
    }

    public void removeMember(UUID projectId, String memberEmail, String requesterEmail) {
        Project project = findProjectOrThrow(projectId);
        checkOwnership(project, requesterEmail);

        User userToRemove = userRepository.findByEmail(memberEmail)
                .orElseThrow(() -> ExceptionFactory.userNotFoundByEmail(memberEmail));

        ProjectMember member = memberRepository.findByProjectAndUser(project, userToRemove)
                .orElseThrow(() -> ExceptionFactory.notFound("Project member not found"));

        memberRepository.delete(member);
    }

    public ProjectMemberResponse updateRole(UUID projectId, String memberEmail, MemberRole newRole, String requesterEmail) {
        Project project = findProjectOrThrow(projectId);
        checkOwnership(project, requesterEmail);

        User memberUser = userRepository.findByEmail(memberEmail)
                .orElseThrow(() -> ExceptionFactory.userNotFoundByEmail(memberEmail));

        ProjectMember member = memberRepository.findByProjectAndUser(project, memberUser)
                .orElseThrow(() -> ExceptionFactory.notFound("Project member not found"));

        member.setRole(newRole);
        memberRepository.save(member);

        return toResponse(member);
    }

    private Project findProjectOrThrow(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> ExceptionFactory.projectNotFound(id));
    }

    private void checkAccess(Project project, String email) {
        if (!project.getCreatedBy().getEmail().equals(email)) {
            throw ExceptionFactory.forbidden("Access denied to this project");
        }
    }

    private void checkOwnership(Project project, String email) {
        if (!project.getCreatedBy().getEmail().equals(email)) {
            throw ExceptionFactory.forbidden("Only owner can manage members");
        }
    }

    private ProjectMemberResponse toResponse(ProjectMember member) {
        return ProjectMemberResponse.builder()
                .id(member.getId())
                .email(member.getUser().getEmail())
                .name(member.getUser().getName())
                .role(member.getRole())
                .dateJoined(member.getDateJoined())
                .build();
    }
}
