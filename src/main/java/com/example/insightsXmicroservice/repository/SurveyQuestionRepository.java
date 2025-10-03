package com.example.insightsXmicroservice.repository;

import com.example.insightsXmicroservice.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, UUID> {

    @Query("SELECT q FROM SurveyQuestion q LEFT JOIN FETCH q.options WHERE q.survey.surveyId = ?1 ORDER BY q.questionOrder")
    List<SurveyQuestion> findBySurveyIdWithOptions(UUID surveyId);
}
