package com.example.insightsXmicroservice.service;

import com.example.insightsXmicroservice.entity.User;
import com.example.insightsXmicroservice.model.GetAllUsersResponse;
import com.example.insightsXmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicDetailsService {

    @Autowired
    UserRepository userRepository;

    public List<GetAllUsersResponse> findAllUsers() {
        // 1. Fetch all entities from the database
        List<User> users = userRepository.findAll();

        // 2. Map each entity to its corresponding DTO
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private GetAllUsersResponse toDTO(User user) {
        GetAllUsersResponse dto = new GetAllUsersResponse();
        // MAPPING: Entity field names (snake_case/camelCase in DB, camelCase in Java)
        // to DTO field names (camelCase)
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setGender(user.getGender());
        return dto;
    }
}