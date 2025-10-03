package com.example.insightsXmicroservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseDto {
    private String response_id;
    private String user_id;
    private String survey_id;
    private String status;
    private Boolean consent_given;
    private Boolean agreement_accepted;
    private Long started_at;
    private Long completed_at;
}
