package com.example.insightsXmicroservice.controller;

import com.example.insightsXmicroservice.model.GetAllUsersResponse;
import com.example.insightsXmicroservice.service.BasicDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users") // Base URL: /api/v1/users
public class BasicDetailsController {

    @Autowired
    BasicDetailsService basicDetailsService;

    /**
     * Endpoint to fetch all users.
     * Maps to GET /api/v1/users
     * @return A list of UserDTO objects and HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<GetAllUsersResponse>> getAllUsers() {
        List<GetAllUsersResponse> users = basicDetailsService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}