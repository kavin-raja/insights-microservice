package com.example.insightsXmicroservice.controller;

import com.example.insightsXmicroservice.model.*;
import com.example.insightsXmicroservice.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    /**
     * GET /api/v1/surveys
     * Fetch all active surveys
     */
    @GetMapping("/surveys")
    public ResponseEntity<List<SurveyResponse>> getAllSurveys() {
        try {
            List<SurveyResponse> surveys = surveyService.getAllSurveys();
            return new ResponseEntity<>(surveys, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/v1/surveys/{surveyId}
     * Get specific survey by ID
     */
    @GetMapping("/surveys/{surveyId}")
    public ResponseEntity<SurveyResponse> getSurveyById(@PathVariable String surveyId) {
        try {
            SurveyResponse survey = surveyService.getSurveyById(surveyId);
            return new ResponseEntity<>(survey, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/v1/surveys/{surveyId}/questions
     * Get all questions with options for a survey
     */
    @GetMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<List<SurveyQuestionResponse>> getSurveyQuestions(@PathVariable String surveyId) {
        try {
            List<SurveyQuestionResponse> questions = surveyService.getSurveyQuestions(surveyId);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * POST /api/v1/survey-responses
     * Start a survey (consent + agreement)
     */
    @PostMapping("/survey-responses")
    public ResponseEntity<SurveyResponseDto> startSurvey(@RequestBody StartSurveyRequest request) {
        try {
            SurveyResponseDto response = surveyService.startSurvey(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * POST /api/v1/survey-responses/{responseId}/answers
     * Submit answers for a survey
     */
    @PostMapping("/survey-responses/{responseId}/answers")
    public ResponseEntity<Void> submitAnswers(@PathVariable String responseId,
                                              @RequestBody SubmitAnswersRequest request) {
        try {
            surveyService.submitAnswers(responseId, request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * POST /api/v1/survey-responses/{responseId}/complete
     * Mark survey as completed
     */
    @PostMapping("/survey-responses/{responseId}/complete")
    public ResponseEntity<Void> completeSurvey(@PathVariable String responseId) {
        try {
            surveyService.completeSurvey(responseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
