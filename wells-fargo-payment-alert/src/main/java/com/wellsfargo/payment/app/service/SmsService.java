package com.wellsfargo.payment.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.wellsfargo.payment.app.model.kafka.AlertMessage;

@Service
public class SmsService {

	@Value("${twilio.trial.number}")
	private String trialNumber;

	public void sendSms(AlertMessage data) {
		Message.creator(new PhoneNumber(data.getPhone()), new PhoneNumber(trialNumber), data.getDetails())
				.create();
	}

}
