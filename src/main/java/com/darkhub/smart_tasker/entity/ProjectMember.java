package com.darkhub.smart_tasker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.darkhub.smart_tasker.entity.enums.MemberRole;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMember {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private LocalDateTime dateJoined;
}
