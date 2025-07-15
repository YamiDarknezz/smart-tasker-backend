package com.darkhub.smart_tasker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMember {

    public enum MemberRole {
        ADMIN, EDITOR, VIEWER, INVITED
    }

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
