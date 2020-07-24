package com.wellsfargo.payment.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wellsfargo.payment.app.model.CustomUserDetails;
import com.wellsfargo.payment.app.model.Role;
import com.wellsfargo.payment.app.model.User;
import com.wellsfargo.payment.app.repository.RoleRepository;
import com.wellsfargo.payment.app.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		//Optional<User> optionalUsers = userRepository.findByUsername(username);

		optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return optionalUser
                .map(CustomUserDetails::new).get();
	}

	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

}
