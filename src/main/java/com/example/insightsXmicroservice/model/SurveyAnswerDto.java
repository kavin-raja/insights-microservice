package com.example.insightsXmicroservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerDto {
    private String question_id;
    private String option_id;
    private String answer_text;
}
