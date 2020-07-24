package com.wellsfargo.payment.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.payment.app.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, Long> {

	Optional<Account> findByAccountNumber(int accountNumber);

	List<Account> findAllByUserId(int userId);

}
