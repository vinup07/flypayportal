package com.flypay.flypayportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flypay.flypayportal.model.UserDetailsResponseDto;
import com.flypay.flypayportal.service.UserService;
import com.flypay.flypayportal.service.impl.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("user/details")
    @Operation(summary = "User Details", description = "get user details")
	 public ResponseEntity<UserDetailsResponseDto> getUserDetails(HttpServletRequest request
	    ) {
        String jwtToken = jwtService.extractJwtFromRequest(request);
        String username = jwtService.extractUsername(jwtToken);
	    return ResponseEntity.ok(userService.getUserDetailsByUsername(username));
	    }

}
