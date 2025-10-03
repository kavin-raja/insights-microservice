package com.example.insightsXmicroservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionResponse {
    private String question_id;
    private String survey_id;
    private String question_text;
    private String question_type;
    private Integer question_order;
    private Boolean is_required;
    private List<QuestionOptionResponse> options;
}
