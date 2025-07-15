package com.darkhub.smart_tasker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

import com.darkhub.smart_tasker.entity.enums.RequestType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIRequest {

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
