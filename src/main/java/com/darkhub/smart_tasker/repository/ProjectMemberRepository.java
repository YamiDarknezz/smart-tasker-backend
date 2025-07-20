package com.darkhub.smart_tasker.repository;

import com.darkhub.smart_tasker.entity.Project;
import com.darkhub.smart_tasker.entity.ProjectMember;
import com.darkhub.smart_tasker.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    List<ProjectMember> findByProject(Project project);
    Optional<ProjectMember> findByProjectAndUser(Project project, User user);
    boolean existsByProjectAndUser(Project project, User user);
    boolean existsByProjectAndUserEmail(Project project, String email);
}