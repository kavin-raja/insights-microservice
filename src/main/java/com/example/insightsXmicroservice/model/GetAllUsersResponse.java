package com.example.insightsXmicroservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Data Transfer Object (DTO) for the User entity.
 * This class defines the structure of data sent to and received from the API,
 * keeping the internal database structure (Entity) separate.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUsersResponse {

    private String fullName;
    private String phoneNumber;
    private String gender;

}
