package com.microservice1.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="AUTHSERVICEAPP")
public interface AuthServiceFeignClient {

	@GetMapping("/api/v1/auth/get-user")
	User getUserByUsername(@RequestParam("username") String username, @RequestHeader("Authorization") String token);
}
