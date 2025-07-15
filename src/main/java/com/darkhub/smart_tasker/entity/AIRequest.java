package com.darkhub.smart_tasker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIRequest {

    public enum RequestType {
        SUGGEST_SUBTASKS, SUMMARIZE
    }

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Column(columnDefinition = "TEXT")
    private String inputText;

    @Column(columnDefinition = "TEXT")
    private String outputText;

    private LocalDateTime createdAt;
}
