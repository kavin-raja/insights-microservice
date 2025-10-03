package com.example.insightsXmicroservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOptionResponse {
    private String option_id;
    private String question_id;
    private String option_text;
    private Integer option_order;
}
