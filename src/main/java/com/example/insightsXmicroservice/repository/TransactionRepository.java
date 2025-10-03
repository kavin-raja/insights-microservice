package com.example.insightsXmicroservice.repository;

import com.example.insightsXmicroservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByUserIdOrderByTimestampDesc(String userId);

    Optional<Transaction> findByReferenceIdAndReferenceType(String referenceId, String referenceType);
}
