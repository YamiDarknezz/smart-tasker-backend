package com.darkhub.smart_tasker.dto;

import java.time.LocalDateTime;

import com.darkhub.smart_tasker.entity.enums.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberResponse {
    private Long id;
    private String email;
    private String name;
    private MemberRole role;
    private LocalDateTime dateJoined;
}