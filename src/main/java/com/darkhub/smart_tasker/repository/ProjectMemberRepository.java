package com.darkhub.smart_tasker.repository;

import com.darkhub.smart_tasker.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
}