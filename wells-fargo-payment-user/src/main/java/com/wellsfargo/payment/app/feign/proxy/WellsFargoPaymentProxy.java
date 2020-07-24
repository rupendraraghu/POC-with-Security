package com.wellsfargo.payment.app.feign.proxy;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wellsfargo.payment.app.model.proxy.AccountProxy;

@FeignClient(name = "wells-fargo-payment-account")
@RibbonClient(name = "wells-fargo-payment-account")
public interface WellsFargoPaymentProxy {

	@GetMapping("/account-management/getAllAccount")
	public List<AccountProxy> getAllAccount();

	@GetMapping("/account-management/getAccountById/{accountId}")
	public AccountProxy getAccount(@PathVariable("accountId") Long accountId);

	@GetMapping("/account-management/getAccountByNo/{accountNumber}")
	public AccountProxy getAccountByAccountNumber(@PathVariable("accountNumber") int accountNumber);

	@GetMapping("/account-management/getAccountByUserId/{userId}")
	public List<AccountProxy> getAccountByUserId(@PathVariable("userId") int userId);

	@PutMapping("/account-management/depositAccount/{accountNumber}/amount/{amount}")
	public String depositAccount(@PathVariable("accountNumber") int accountNumber,
			@PathVariable("amount") BigDecimal amount);

	@PutMapping("/account-management/withdrawAccount/{accountNumber}/amount/{amount}")
	public String withdrawAccount(@PathVariable("accountNumber") int accountNumber,
			@PathVariable("amount") BigDecimal amount);

	@DeleteMapping("/account-management/deleteAccountById/{accountId}")
	public String deleteAccountById(@PathVariable("accountId") Long accountId);

	@PostMapping("/account-management/createAccount")
	public String createAccount(@RequestBody AccountProxy account);

}
