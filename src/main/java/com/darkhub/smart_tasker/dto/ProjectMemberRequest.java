package com.darkhub.smart_tasker.dto;

import com.darkhub.smart_tasker.entity.enums.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberRequest {
    private String email;
    private MemberRole role;
}
