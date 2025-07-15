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
public class File {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Project project;

    private String filename;

    private String url;

    @ManyToOne
    private User uploadedBy;

    private LocalDateTime dateUploaded;
}
