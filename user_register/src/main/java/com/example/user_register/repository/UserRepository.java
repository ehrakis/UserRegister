package com.example.user_register.repository;

import com.example.user_register.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findById(String id);
}
