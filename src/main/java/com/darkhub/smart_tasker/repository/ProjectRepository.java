package com.darkhub.smart_tasker.repository;

import com.darkhub.smart_tasker.entity.Project;
import com.darkhub.smart_tasker.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByCreatedBy(User createdBy);
}