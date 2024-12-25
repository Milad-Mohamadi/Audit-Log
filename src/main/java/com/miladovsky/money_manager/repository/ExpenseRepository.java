package com.miladovsky.money_manager.repository;


import com.miladovsky.money_manager.domain.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    // Custom queries if needed
}