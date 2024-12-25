package com.miladovsky.money_manager.repository;


import com.miladovsky.money_manager.domain.Withdrawal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WithdrawalRepository extends MongoRepository<Withdrawal, String> {
    // Custom queries if needed
}