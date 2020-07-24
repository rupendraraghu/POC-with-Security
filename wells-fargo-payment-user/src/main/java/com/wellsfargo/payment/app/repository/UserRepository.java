package com.wellsfargo.payment.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wellsfargo.payment.app.model.User;

public interface UserRepository extends MongoRepository<User, Integer> {

	User findByUsername(String username);

	Optional<User> findByEmail(String email);

	Optional<User> findByPhoneNumber(String phoneNumber);

}
