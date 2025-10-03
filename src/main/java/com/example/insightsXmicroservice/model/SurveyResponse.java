package com.example.insightsXmicroservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponse {
    private String survey_id;
    private String title;
    private String description;
    private String brand;
    private String purpose;
    private List<String> data_points;
    private String status;
    private Integer reward_points;
    private Integer estimated_time_minutes;
}
