package com.employee.test.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.employee.test.api.serviceImpl.DomainUserDetailsServiceImpl;

@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	
	public ApplicationSecurityConfiguration(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder
		.userDetailsService(this.userDetailsService)
		.passwordEncoder(PasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
        .antMatchers("/roles").hasAnyRole("ADMIN")
        .antMatchers("/users").hasAnyRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/employees").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/employees/{id}").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET,"/employees").hasAnyRole("USER","ADMIN")
        .antMatchers(HttpMethod.PUT,"/employees/{id}").hasAnyRole("USER","ADMIN")
        .antMatchers(HttpMethod.GET,"/employees/*").hasAnyRole("USER","ADMIN")
        .antMatchers("/h2-console/**").permitAll()
        .antMatchers(HttpMethod.GET,"/").permitAll()
    	.anyRequest().fullyAuthenticated()
        .and()
        .httpBasic();
	}

	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
