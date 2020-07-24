package com.wellsfargo.payment.app.kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.wellsfargo.payment.app.model.kafka.AlertMessage;
import com.wellsfargo.payment.app.service.SmsService;

@Component
public class KafkaConsumerPaymentAlert {

	@Autowired
	private SmsService smsService;
	
	@KafkaListener(groupId = "transactionalert-1",topics = "transactionalert", containerFactory = "alertMessageKafkaListenerContainerFactory")
	public void sendSms(AlertMessage alertMessage) {
		smsService.sendSms(alertMessage);

	}

}
