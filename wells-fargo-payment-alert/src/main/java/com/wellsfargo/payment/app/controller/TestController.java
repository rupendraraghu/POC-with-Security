package com.wellsfargo.payment.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.payment.app.model.kafka.AlertMessage;
import com.wellsfargo.payment.app.service.SmsService;

@RestController
public class TestController {
	@Autowired
	private SmsService smsService;

	@GetMapping("/test")
	private String test() {
		smsService.sendSms(new AlertMessage("rr.r@gmail.com", "+918109358283", "Its Working"));
		return "Hello";
	}

}
