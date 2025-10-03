package com.example.insightsXmicroservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartSurveyRequest {
    private String user_id;
    private String survey_id;
    private Boolean consent_given;
    private Boolean agreement_accepted;
}
