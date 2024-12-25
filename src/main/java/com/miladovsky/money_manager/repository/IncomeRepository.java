package com.miladovsky.money_manager.repository;


import com.miladovsky.money_manager.domain.Income;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IncomeRepository extends MongoRepository<Income, String> {
    // Custom queries if needed
}