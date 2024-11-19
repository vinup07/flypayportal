package com.flypay.flypayportal.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flypay.flypayportal.dao.User;
import com.flypay.flypayportal.repository.UserRepository;
import com.flypay.flypayportal.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepositiry;

	@Override
	public User registerUser(User user) {
		User saveUser = userRepositiry.save(user);
		if(Objects.nonNull(saveUser)) {
			return saveUser;
		}
		return null;
	}

}
