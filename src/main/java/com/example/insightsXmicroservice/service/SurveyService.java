package com.example.insightsXmicroservice.service;

import com.example.insightsXmicroservice.entity.*;
import com.example.insightsXmicroservice.model.*;
import com.example.insightsXmicroservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyQuestionRepository surveyQuestionRepository;

    @Autowired
    private UserSurveyResponseRepository userSurveyResponseRepository;

    @Autowired
    private SurveyAnswerRepository surveyAnswerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Get all active surveys
    public List<SurveyResponse> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findByStatusOrderByCreatedAtDesc("ACTIVE");
        return surveys.stream()
                .map(this::toSurveyResponse)
                .collect(Collectors.toList());
    }

    // Get specific survey by ID
    public SurveyResponse getSurveyById(String surveyId) {
        Survey survey = surveyRepository.findById(UUID.fromString(surveyId))
                .orElseThrow(() -> new RuntimeException("Survey not found"));
        return toSurveyResponse(survey);
    }

    // Get questions for a survey
    public List<SurveyQuestionResponse> getSurveyQuestions(String surveyId) {
        List<SurveyQuestion> questions = surveyQuestionRepository
                .findBySurveyIdWithOptions(UUID.fromString(surveyId));
        return questions.stream()
                .map(this::toSurveyQuestionResponse)
                .collect(Collectors.toList());
    }

    // Start a survey
    @Transactional
    public SurveyResponseDto startSurvey(StartSurveyRequest request) {
        UserSurveyResponse response = new UserSurveyResponse();
        response.setUserId(request.getUser_id());
        response.setSurveyId(UUID.fromString(request.getSurvey_id()));
        response.setConsentGiven(request.getConsent_given());
        response.setAgreementAccepted(request.getAgreement_accepted());
        response.setStatus("IN_PROGRESS");

        UserSurveyResponse saved = userSurveyResponseRepository.save(response);
        return toSurveyResponseDto(saved);
    }

    // Submit answers
    @Transactional
    public void submitAnswers(String responseId, SubmitAnswersRequest request) {
        UUID responseUuid = UUID.fromString(responseId);

        for (SurveyAnswerDto answerDto : request.getAnswers()) {
            SurveyAnswer answer = new SurveyAnswer();
            answer.setResponseId(responseUuid);
            answer.setQuestionId(UUID.fromString(answerDto.getQuestion_id()));

            if (answerDto.getOption_id() != null) {
                answer.setOptionId(UUID.fromString(answerDto.getOption_id()));
            }
            if (answerDto.getAnswer_text() != null) {
                answer.setAnswerText(answerDto.getAnswer_text());
            }

            surveyAnswerRepository.save(answer);
        }
    }

    // Complete survey
    @Transactional
    public void completeSurvey(String responseId) {
        UserSurveyResponse response = userSurveyResponseRepository
                .findById(UUID.fromString(responseId))
                .orElseThrow(() -> new RuntimeException("Survey response not found"));

        // Check if already completed to avoid duplicate points
        if ("COMPLETED".equals(response.getStatus())) {
            System.out.println("⚠️ Survey already completed: " + responseId);
            return;
        }

        // Update survey status
        response.setStatus("COMPLETED");
        response.setCompletedAt(LocalDateTime.now());
        userSurveyResponseRepository.save(response);

        // Get survey to find reward points
        Survey survey = surveyRepository.findById(response.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        // Check if transaction already exists for this survey response
        Optional<Transaction> existingTransaction = transactionRepository
                .findByReferenceIdAndReferenceType(responseId, "SURVEY");

        if (existingTransaction.isPresent()) {
            System.out.println("⚠️ Transaction already exists for survey response: " + responseId);
            return;
        }

        // Create transaction for reward points
        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8));
        transaction.setUserId(response.getUserId());
        transaction.setType("CREDIT");
        transaction.setAmount(new BigDecimal(survey.getRewardPoints()));
        transaction.setDescription("Survey Completed: " + survey.getTitle());
        transaction.setTimestamp(System.currentTimeMillis());
        transaction.setStatus("SUCCESS");
        transaction.setReferenceId(responseId);
        transaction.setReferenceType("SURVEY");

        transactionRepository.save(transaction);

        System.out.println("✅ Awarded " + survey.getRewardPoints() + " points to user " + response.getUserId() + " for completing survey");
    }

    // Helper method to convert entity to DTO
    private SurveyResponse toSurveyResponse(Survey survey) {
        SurveyResponse response = new SurveyResponse();
        response.setSurvey_id(survey.getSurveyId().toString());
        response.setTitle(survey.getTitle());
        response.setDescription(survey.getDescription());
        response.setBrand(survey.getBrand());
        response.setPurpose(survey.getPurpose());
        response.setData_points(Arrays.asList(survey.getDataPoints()));
        response.setStatus(survey.getStatus());
        response.setReward_points(survey.getRewardPoints());
        response.setEstimated_time_minutes(survey.getEstimatedTimeMinutes());
        return response;
    }

    private SurveyQuestionResponse toSurveyQuestionResponse(SurveyQuestion question) {
        SurveyQuestionResponse response = new SurveyQuestionResponse();
        response.setQuestion_id(question.getQuestionId().toString());
        response.setSurvey_id(question.getSurvey().getSurveyId().toString());
        response.setQuestion_text(question.getQuestionText());
        response.setQuestion_type(question.getQuestionType());
        response.setQuestion_order(question.getQuestionOrder());
        response.setIs_required(question.getIsRequired());

        if (question.getOptions() != null) {
            List<QuestionOptionResponse> options = question.getOptions().stream()
                    .map(this::toQuestionOptionResponse)
                    .collect(Collectors.toList());
            response.setOptions(options);
        }

        return response;
    }

    private QuestionOptionResponse toQuestionOptionResponse(QuestionOption option) {
        QuestionOptionResponse response = new QuestionOptionResponse();
        response.setOption_id(option.getOptionId().toString());
        response.setQuestion_id(option.getQuestion().getQuestionId().toString());
        response.setOption_text(option.getOptionText());
        response.setOption_order(option.getOptionOrder());
        return response;
    }

    private SurveyResponseDto toSurveyResponseDto(UserSurveyResponse response) {
        SurveyResponseDto dto = new SurveyResponseDto();
        dto.setResponse_id(response.getResponseId().toString());
        dto.setUser_id(response.getUserId());
        dto.setSurvey_id(response.getSurveyId().toString());
        dto.setStatus(response.getStatus());
        dto.setConsent_given(response.getConsentGiven());
        dto.setAgreement_accepted(response.getAgreementAccepted());
        dto.setStarted_at(response.getStartedAt().toEpochSecond(ZoneOffset.UTC));

        if (response.getCompletedAt() != null) {
            dto.setCompleted_at(response.getCompletedAt().toEpochSecond(ZoneOffset.UTC));
        }

        return dto;
    }
}
