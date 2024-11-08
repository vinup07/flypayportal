/**
 * 
 */
package com.flypay.flypayportal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flypay.flypayportal.dao.User;
import com.flypay.flypayportal.service.UserService;

/**
 * @author kaushik.udavant
 *
 */
@RestController
@RequestMapping("/api/v1/")
public class UserController {
	
	private UserService userService;

	@PostMapping("register")
	public ResponseEntity<User> resgister(@RequestBody User user){
		User registeredUser = userService.registerUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
