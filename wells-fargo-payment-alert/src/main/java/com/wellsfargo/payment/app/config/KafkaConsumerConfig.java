package com.wellsfargo.payment.app.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.wellsfargo.payment.app.model.kafka.AlertMessage;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	
	@Value("${bootstrap.servers.config}")
	private String bootstrapServersConfig;

	@Bean
	public ConsumerFactory<String, AlertMessage> alertMessageConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "transactionalert-1");
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(AlertMessage.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, AlertMessage> alertMessageKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, AlertMessage> factory = new ConcurrentKafkaListenerContainerFactory<String, AlertMessage>();
		factory.setConsumerFactory(alertMessageConsumerFactory());
		return factory;
	}

}
