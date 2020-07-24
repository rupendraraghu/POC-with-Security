package com.wellsfargo.payment.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.payment.app.exception.AccountNotFoundException;
import com.wellsfargo.payment.app.model.Account;
import com.wellsfargo.payment.app.service.AccountService;

@RestController
@RequestMapping("/account-management")
public class AccountController {
	@Autowired
	private AccountService accountService;

	@GetMapping("/test")
	private String test() {
		return "Hello";
	}

	@GetMapping("/getAllAccount")
	public List<Account> getAllAccount() {
		return accountService.getAccounts();
	}

	@GetMapping("/getAccountById/{accountId}")
	public Account getAccount(@PathVariable Long accountId) {
		Optional<Account> account = accountService.getAccount(accountId);
		return account.orElseThrow(
				() -> new AccountNotFoundException("Account with Account Id : " + accountId + " doesn't exist"));
	}

	@GetMapping("/getAccountByNo/{accountNumber}")
	public Account getAccountByAccountNumber(@PathVariable int accountNumber) {
		Optional<Account> account = accountService.getAccountByAccountNumber(accountNumber);
		return account.orElseThrow(
				() -> new AccountNotFoundException("Account with Account No : " + accountNumber + " doesn't exist"));

	}

	@GetMapping("/getAccountByUserId/{userId}")
	public List<Account> getAccountByUserId(@PathVariable int userId) {
		return accountService.getAccountByUserId(userId);

	}

	@PutMapping("/depositAccount/{accountNumber}/amount/{amount}")
	public String depositAccount(@PathVariable int accountNumber, @PathVariable BigDecimal amount) {
		Account account = getAccountByAccountNumber(accountNumber);
		return accountService.deposit(account, amount);
	}

	@PutMapping("/withdrawAccount/{accountNumber}/amount/{amount}")
	public String withdrawAccount(@PathVariable int accountNumber, @PathVariable BigDecimal amount) {
		Account account = getAccountByAccountNumber(accountNumber);
		return accountService.withdraw(account, amount);
	}

	@DeleteMapping("/deleteAccountById/{accountId}")
	public String deleteAccountById(@PathVariable Long accountId) {
		return accountService.deleteAccount(accountId);
	}

	@PostMapping("/createAccount")
	public String createAccount(@Valid @RequestBody Account account) {
		return accountService.createAccount(account);
	}
}
