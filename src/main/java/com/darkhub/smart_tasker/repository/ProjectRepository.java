package com.darkhub.smart_tasker.repository;

import com.darkhub.smart_tasker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}