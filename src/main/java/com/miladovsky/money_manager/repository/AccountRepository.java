package com.miladovsky.money_manager.repository;


import com.miladovsky.money_manager.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
    // Custom queries if needed
}