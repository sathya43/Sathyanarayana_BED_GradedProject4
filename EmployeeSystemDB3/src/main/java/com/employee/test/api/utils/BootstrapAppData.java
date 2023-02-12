package com.employee.test.api.utils;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.employee.test.api.entity.Role;
import com.employee.test.api.entity.User;
import com.employee.test.api.repository.RoleJpaRepository;
import com.employee.test.api.repository.UserJpaRepository;

@Configuration
public class BootstrapAppData {
	
	private final UserJpaRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleJpaRepository roleJpaRepository;
	
	public BootstrapAppData(UserJpaRepository userRepository, PasswordEncoder passwordEncoder, RoleJpaRepository roleJpaRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleJpaRepository = roleJpaRepository;
	}
	
	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void insertAppData(ApplicationReadyEvent event) {
		System.out.println("Application is ready :: ");
		System.out.println("Inserting the test data :: ");
		
		User kiran = new User();
		kiran.setUsername("kiran");
		kiran.setEmail("kiran@gmail.com");
		kiran.setPassword(passwordEncoder.encode("welcome"));
		
		User sai = new User();
		sai.setUsername("sai");
		sai.setEmail("sai@gmail.com");
		sai.setPassword(passwordEncoder.encode("welcome"));
	 
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		
		kiran.addRole(adminRole);
		sai.addRole(userRole);
		
		userRepository.save(kiran);
		userRepository.save(sai);
		
	}
	
}
