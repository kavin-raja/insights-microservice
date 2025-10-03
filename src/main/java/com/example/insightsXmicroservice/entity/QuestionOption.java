package com.example.insightsXmicroservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "question_options")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOption {

    @Id
    @Column(name = "option_id")
    private UUID optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private SurveyQuestion question;

    @Column(name = "option_text", nullable = false)
    private String optionText;

    @Column(name = "option_order", nullable = false)
    private Integer optionOrder;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (optionId == null) {
            optionId = UUID.randomUUID();
        }
        createdAt = LocalDateTime.now();
    }
}
