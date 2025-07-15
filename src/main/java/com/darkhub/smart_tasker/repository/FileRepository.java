package com.darkhub.smart_tasker.repository;

import com.darkhub.smart_tasker.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {
}