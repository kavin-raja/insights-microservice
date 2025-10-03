package com.example.insightsXmicroservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "surveys")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Survey {

    @Id
    @Column(name = "survey_id")
    private UUID surveyId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "purpose", nullable = false)
    private String purpose;

    @Column(name = "data_points")
    private String[] dataPoints;

    @Column(name = "status")
    private String status = "ACTIVE";

    @Column(name = "reward_points")
    private Integer rewardPoints = 0;

    @Column(name = "estimated_time_minutes")
    private Integer estimatedTimeMinutes = 5;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SurveyQuestion> questions;

    @PrePersist
    protected void onCreate() {
        if (surveyId == null) {
            surveyId = UUID.randomUUID();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
