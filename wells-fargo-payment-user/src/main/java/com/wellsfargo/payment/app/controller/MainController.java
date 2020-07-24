package com.wellsfargo.payment.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.payment.app.exception.UserNotFoundException;
import com.wellsfargo.payment.app.feign.proxy.WellsFargoPaymentProxy;
import com.wellsfargo.payment.app.kafka.producer.KafkaProducerPaymentAlert;
import com.wellsfargo.payment.app.model.User;
import com.wellsfargo.payment.app.model.kafka.AlertMessage;
import com.wellsfargo.payment.app.model.proxy.AccountProxy;
import com.wellsfargo.payment.app.service.UserService;
import com.wellsfargo.payment.app.service.impl.CustomUserDetailsService;

@RestController
@RequestMapping("/secured")
public class MainController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private WellsFargoPaymentProxy wellsFargoPaymentProxy;

	@Autowired
	private KafkaProducerPaymentAlert kafkaProducerPaymentAlert;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/admin/add")
	public String addUserByAdmin(@RequestBody User user) {
		customUserDetailsService.saveUser(user);
		return "user added successfully...";
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/admin/all")
	public String securedHello() {
		return "Secured Hello";
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/getCustomerByEmail/{email}")
	public User getCustomerByEmail(@PathVariable String email) {
		return userService.getUserByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User with email :" + email + " not found"));
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/getCustomerByPhoneNumber/{phoneNumber}")
	public User getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
		return userService.getUserByPhoneNumber(phoneNumber)
				.orElseThrow(() -> new UserNotFoundException("User with Phone Number :" + phoneNumber + " not found"));

	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/getAllAccounts")
	public List<AccountProxy> getAllAccounts() {
		return wellsFargoPaymentProxy.getAllAccount();
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/getAccountByAccountId/{accountId}")
	public AccountProxy getAccount(@PathVariable Long accountId) {
		return wellsFargoPaymentProxy.getAccount(accountId);
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/getAccountByAccountNumber/{accountNumber}")
	public AccountProxy getAccountByAccountNumber(@PathVariable int accountNumber) {
		return wellsFargoPaymentProxy.getAccountByAccountNumber(accountNumber);
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/getAllAccounts/{userId}")
	public List<AccountProxy> getAllAccounts(@PathVariable int userId) {
		return wellsFargoPaymentProxy.getAccountByUserId(userId);
	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/depositAccount/userId/{userId}/accountNumber/{accountNumber}/amount/{amount}")
	public String depositAccount(@PathVariable int userId, @PathVariable int accountNumber,
			@PathVariable BigDecimal amount) {
		String depositMessage = wellsFargoPaymentProxy.depositAccount(accountNumber, amount);
		Optional<User> userById = userService.getUserById(userId);
		if (userById.isPresent()) {
			AlertMessage message = new AlertMessage(userById.get().getEmail(),
					userById.get().getPhoneNumber(), depositMessage);
			kafkaProducerPaymentAlert.send(message);
		}
		return depositMessage;
	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/withdrawAccount/userId/{userId}/accountNumber/{accountNumber}/amount/{amount}")
	public String withdrawAccount(@PathVariable int userId, @PathVariable int accountNumber,
			@PathVariable BigDecimal amount) {
		String withdrawMessage = wellsFargoPaymentProxy.withdrawAccount(accountNumber, amount);
		Optional<User> userById = userService.getUserById(userId);
		if (userById.isPresent()) {
			AlertMessage message = new AlertMessage(userById.get().getEmail(),
					userById.get().getPhoneNumber(), withdrawMessage);
			kafkaProducerPaymentAlert.send(message);
		}
		return withdrawMessage;
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/deleteAccountById/{accountId}")
	public String deleteAccountById(@PathVariable Long accountId) {
		return wellsFargoPaymentProxy.deleteAccountById(accountId);
	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/createAccount")
	public String createAccount(@RequestBody AccountProxy account) {
		return wellsFargoPaymentProxy.createAccount(account);
	}

}
