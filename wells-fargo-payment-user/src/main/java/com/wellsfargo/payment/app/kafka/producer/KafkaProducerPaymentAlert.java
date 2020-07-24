package com.wellsfargo.payment.app.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.wellsfargo.payment.app.model.kafka.AlertMessage;

@Component
public class KafkaProducerPaymentAlert {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${transaction.alert.topic}")
	private String kafkaTopic;

	public void send(AlertMessage message) {

		kafkaTemplate.send(kafkaTopic, message);
	}

}
