package com.darkhub.smart_tasker.repository;

import com.darkhub.smart_tasker.entity.UserScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserScheduleEntryRepository extends JpaRepository<UserScheduleEntry, UUID> {

    List<UserScheduleEntry> findByUserId(UUID userId);

    List<UserScheduleEntry> findByProjectId(UUID projectId);
}