package com.authservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authservice.dto.ApiResponse;
import com.authservice.dto.UpdatePasswordDto;
import com.authservice.dto.UserDto;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ApiResponse<String> register(UserDto userDto) {

		if (userRepository.existsByUsername(userDto.getUsername())) {
			ApiResponse<String> response = new ApiResponse();
			response.setMessage("Registration Failed");
			response.setStatus(500);
			response.setData("User already exists with username : " + userDto.getUsername());

			return response;
		}

		if (userRepository.existsByEmail(userDto.getEmail())) {
			ApiResponse<String> response = new ApiResponse();
			response.setMessage("Registration Failed");
			response.setStatus(500);
			response.setData("User already exists with email : " + userDto.getEmail());

			return response;
		}

		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userRepository.save(user);

		ApiResponse<String> response = new ApiResponse();
		response.setMessage("Registration Done Successfully");
		response.setStatus(201);
		response.setData("Registration done successfully with email : " + user.getEmail());

		return response;
	}
	
	public ApiResponse<String> updateNewPassword(UpdatePasswordDto updatePasswordDto){
		
		if(!userRepository.existsByUsername(updatePasswordDto.getUsername())) {
			ApiResponse<String> response = new ApiResponse();
			response.setMessage("User name not Found");
			response.setStatus(500);
			response.setData("User not exists in our system : "+updatePasswordDto.getUsername());
			
			return response;
		}
		
		if(!userRepository.existsByEmail(updatePasswordDto.getEmail())) {
			ApiResponse<String> response = new ApiResponse();
			response.setMessage("User email not Found");
			response.setStatus(500);
			response.setData("User not exits in you system : "+updatePasswordDto.getEmail());
			
			return response;
		}
		
		User user = userRepository.findByEmail(updatePasswordDto.getEmail());
		
		if(BCrypt.checkpw(updatePasswordDto.getOldPassword(), user.getPassword())) {
			user.setPassword(updatePasswordDto.getNewPassword());
			userRepository.save(user);
			
			ApiResponse<String> response = new ApiResponse();
			response.setMessage("Password Updated Successfully");
			response.setStatus(200);
			response.setData("Password Updated successfully wrt to username : "+user.getUsername());
			
			return response;
		
		}
		
		return null;
	}
}
