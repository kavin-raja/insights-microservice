package com.example.insightsXmicroservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "survey_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswer {

    @Id
    @Column(name = "answer_id")
    private UUID answerId;

    @Column(name = "response_id", nullable = false)
    private UUID responseId;

    @Column(name = "question_id", nullable = false)
    private UUID questionId;

    @Column(name = "option_id")
    private UUID optionId;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "answered_at")
    private LocalDateTime answeredAt;

    @PrePersist
    protected void onCreate() {
        if (answerId == null) {
            answerId = UUID.randomUUID();
        }
        answeredAt = LocalDateTime.now();
    }
}
