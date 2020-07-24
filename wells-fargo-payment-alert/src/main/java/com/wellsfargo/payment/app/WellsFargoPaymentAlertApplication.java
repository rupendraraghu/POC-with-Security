package com.wellsfargo.payment.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WellsFargoPaymentAlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellsFargoPaymentAlertApplication.class, args);
	}

}
