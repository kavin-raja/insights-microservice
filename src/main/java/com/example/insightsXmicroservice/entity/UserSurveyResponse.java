package com.example.insightsXmicroservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_survey_responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSurveyResponse {

    @Id
    @Column(name = "response_id")
    private UUID responseId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "survey_id", nullable = false)
    private UUID surveyId;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "status")
    private String status = "IN_PROGRESS";

    @Column(name = "consent_given")
    private Boolean consentGiven = false;

    @Column(name = "agreement_accepted")
    private Boolean agreementAccepted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (responseId == null) {
            responseId = UUID.randomUUID();
        }
        startedAt = LocalDateTime.now();
        createdAt = LocalDateTime.now();
    }
}
