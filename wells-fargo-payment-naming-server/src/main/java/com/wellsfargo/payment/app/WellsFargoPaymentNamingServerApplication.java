package com.wellsfargo.payment.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WellsFargoPaymentNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellsFargoPaymentNamingServerApplication.class, args);
	}

}
