package com.example.insightsXmicroservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "type", nullable = false)
    private String type; // CREDIT or DEBIT

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "timestamp", nullable = false)
    private Long timestamp;

    @Column(name = "status")
    private String status = "SUCCESS";

    @Column(name = "reference_id")
    private String referenceId; // UUID of survey response or coupon

    @Column(name = "reference_type")
    private String referenceType; // SURVEY, COUPON, etc.
}
