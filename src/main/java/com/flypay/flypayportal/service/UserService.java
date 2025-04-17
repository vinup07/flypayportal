/**
 * 
 */
package com.flypay.flypayportal.service;

import com.flypay.flypayportal.model.UserDetailsResponseDto;

/**
 * 
 */
public interface UserService {
	
	 public UserDetailsResponseDto getUserDetailsByUsername(String token);
}
