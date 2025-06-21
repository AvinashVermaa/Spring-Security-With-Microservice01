package com.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authservice.service.CustomUserDetailsService;
import com.authservice.service.JwtFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig{
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	public String[] publicEndPointsUrl = {
			 "/api/v1/auth/register",
		        "/api/v1/auth/login",
		        "/api/v1/auth/update-password",
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
	public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}
	
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider daoAuthProvider =
				new DaoAuthenticationProvider();
		
		daoAuthProvider.setUserDetailsService(userDetailsService);
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthProvider;
	}
	
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
	
		http.authorizeHttpRequests(req->{
			req.requestMatchers(publicEndPointsUrl)
			.permitAll()
			.requestMatchers("api/v1/admin/welcome").hasAnyRole("ADMIN","USER")
			.anyRequest()
			.authenticated();
		});
		//.authenticationProvider(authProvider());
		//.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.csrf().disable().build();
	}
	
	
}