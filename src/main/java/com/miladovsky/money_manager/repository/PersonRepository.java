package com.miladovsky.money_manager.repository;


import com.miladovsky.money_manager.domain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
    // Custom queries if needed
}