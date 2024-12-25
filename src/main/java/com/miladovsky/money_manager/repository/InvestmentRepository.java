package com.miladovsky.money_manager.repository;


import com.miladovsky.money_manager.domain.Investment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvestmentRepository extends MongoRepository<Investment, String> {
    // Custom queries if needed
}