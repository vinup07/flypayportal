package com.flypay.flypayportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flypay.flypayportal.ExceptionHandler.UserNotFoundException;
import com.flypay.flypayportal.model.User;
import com.flypay.flypayportal.model.UserDetailsResponseDto;
import com.flypay.flypayportal.repository.UserRepository;
import com.flypay.flypayportal.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetailsResponseDto getUserDetailsByUsername(String username) {
	    try {
		User user = userRepository.findByUsername(username).orElse(null);
		if (user == null) {
			throw new UserNotFoundException("User not found: " + username);
		}
	    UserDetailsResponseDto userDetailsResponseDto = new UserDetailsResponseDto();
        userDetailsResponseDto.setUsername(user.getUsername());
        userDetailsResponseDto.setRole(user.getRole().name());

        return userDetailsResponseDto;
	    } catch (UserNotFoundException e) {
	        log.error("User not found: " + e.getMessage());
	        throw new RuntimeException(e.getMessage(), e);
	    }

	}

}
