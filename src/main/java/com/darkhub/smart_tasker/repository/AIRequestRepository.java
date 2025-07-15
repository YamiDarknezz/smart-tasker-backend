package com.darkhub.smart_tasker.repository;

import com.darkhub.smart_tasker.entity.AIRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AIRequestRepository extends JpaRepository<AIRequest, UUID> {
}