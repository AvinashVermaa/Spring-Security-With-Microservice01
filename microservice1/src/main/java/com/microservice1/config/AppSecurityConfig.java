package com.microservice1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microservice1.filter.JwtFilter;
import com.microservice1.filter.JwtService;

@Configuration
public class AppSecurityConfig{
	
	@Autowired
	private JwtFilter jwtFilter;
	
	private String[] publicEndPointUrl = {
			"/v3/api-docs/**",
	        "/swagger-ui/**",
	        "/swagger-ui.html",
	        "/swagger-resources/**",
	        "/webjars/**",
	        "/actuator/**", 
	        "/eureka/**"	
	};
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(req->{
			req.requestMatchers(publicEndPointUrl)
			.permitAll()
			//.requestMatchers("/welcome").hasRole("USER") //Ensure no ROLE_ prefix is used here
			.anyRequest()
			.authenticated();
		})
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.csrf().disable().build();
	}
	
}