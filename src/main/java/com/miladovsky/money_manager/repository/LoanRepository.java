package com.miladovsky.money_manager.repository;


import com.miladovsky.money_manager.domain.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanRepository extends MongoRepository<Loan, String> {
    // Custom queries if needed
}