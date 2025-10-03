package com.example.insightsXmicroservice.repository;

import com.example.insightsXmicroservice.entity.UserSurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserSurveyResponseRepository extends JpaRepository<UserSurveyResponse, UUID> {
}
