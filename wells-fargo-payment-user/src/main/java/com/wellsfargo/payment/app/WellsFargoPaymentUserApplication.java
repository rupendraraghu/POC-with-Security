package com.wellsfargo.payment.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.wellsfargo.payment.app.model.Role;
import com.wellsfargo.payment.app.repository.RoleRepository;

@SpringBootApplication
@EnableFeignClients("com.wellsfargo.payment.app")
@EnableDiscoveryClient
public class WellsFargoPaymentUserApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(WellsFargoPaymentUserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = roleRepository.findByRole("ADMIN");
		if (adminRole == null) {
			Role newAdminRole = new Role();
			newAdminRole.setId(1);
			newAdminRole.setRole("ADMIN");
			roleRepository.save(newAdminRole);
		}

		Role userRole = roleRepository.findByRole("USER");
		if (userRole == null) {
			Role newUserRole = new Role();
			newUserRole.setId(2);
			newUserRole.setRole("USER");
			roleRepository.save(newUserRole);
		}

	}

}
