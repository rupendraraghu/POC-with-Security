package com.wellsfargo.payment.app.service;

import java.util.List;
import java.util.Optional;

import com.wellsfargo.payment.app.model.User;

public interface UserService {

	List<User> getAllUsers();

	Optional<User> getUserById(int id);

	Optional<User> getUserByEmail(String email);

	Optional<User> getUserByPhoneNumber(String phoneNumber);
}
