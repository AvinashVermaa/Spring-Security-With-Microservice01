package com.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.dto.ApiResponse;
import com.authservice.dto.LoginDto;
import com.authservice.dto.UpdatePasswordDto;
import com.authservice.dto.UserDto;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;
import com.authservice.service.AuthService;
import com.authservice.service.JwtService;



@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<String>> register(@RequestBody UserDto userDto) {
		userDto.setRole("ROLE_ADMIN");
		ApiResponse<String> response = authService.register(userDto);

		return new ResponseEntity<ApiResponse<String>>(response, HttpStatusCode.valueOf(response.getStatus()));

	}

	@PutMapping("/update-password")
	public ResponseEntity<ApiResponse<String>> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {

		ApiResponse<String> response = authService.updateNewPassword(updatePasswordDto);

		return new ResponseEntity<ApiResponse<String>>(response, HttpStatusCode.valueOf(response.getStatus()));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> loginCheck(@RequestBody LoginDto loginDto) {

		ApiResponse<String> response = new ApiResponse();

		UsernamePasswordAuthenticationToken unamepassToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());

		try {
			Authentication authenticate = authManager.authenticate(unamepassToken);

			if (authenticate.isAuthenticated()) {
				String jwtToken = jwtService.generateJwtToken(loginDto.getUsername(),
						authenticate.getAuthorities().iterator().next().getAuthority());

				response.setMessage("Login Successfully");
				response.setStatus(200);
				response.setData(jwtToken);

				return new ResponseEntity<ApiResponse<String>>(response, HttpStatusCode.valueOf(response.getStatus()));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setMessage("Login Failed");
		response.setStatus(401);
		response.setData("Unauthorized Access");

		return new ResponseEntity<ApiResponse<String>>(response, HttpStatusCode.valueOf(response.getStatus()));
	}
	
	 @GetMapping("/get-user")
	 public User getUser(@RequestParam String username) {
		 return userRepository.findByUsername(username);
	 }
}
