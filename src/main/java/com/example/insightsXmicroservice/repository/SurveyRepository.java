package com.example.insightsXmicroservice.repository;

import com.example.insightsXmicroservice.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, UUID> {

    List<Survey> findByStatusOrderByCreatedAtDesc(String status);

    @Query("SELECT s FROM Survey s LEFT JOIN FETCH s.questions WHERE s.surveyId = ?1")
    Survey findByIdWithQuestions(UUID surveyId);
}
