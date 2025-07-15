package com.darkhub.smart_tasker.repository;

import com.darkhub.smart_tasker.entity.UserScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserScheduleEntryRepository extends JpaRepository<UserScheduleEntry, UUID> {
}