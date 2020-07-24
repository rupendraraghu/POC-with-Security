package com.wellsfargo.payment.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wellsfargo.payment.app.model.Role;

public interface RoleRepository extends MongoRepository<Role, Integer> {

	Role findByRole(String role);

}
