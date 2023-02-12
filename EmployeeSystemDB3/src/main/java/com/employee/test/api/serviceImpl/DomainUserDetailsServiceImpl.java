package com.employee.test.api.serviceImpl;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employee.test.api.entity.DomainUserDetails;
import com.employee.test.api.entity.User;
import com.employee.test.api.repository.UserJpaRepository;

@Service
@Primary
public class DomainUserDetailsServiceImpl implements UserDetailsService{
	
	private final UserJpaRepository userJpaRepository;
	
	public DomainUserDetailsServiceImpl(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Authenticating user");
		Optional<User> optionalUserOptional = this.userJpaRepository.findByUsername(username);
		if(optionalUserOptional.isPresent()) {
			User user = optionalUserOptional.get();
			return new DomainUserDetails(user);
			
		}else {
			throw new UsernameNotFoundException("Invalid User name");
		}
	}

}
