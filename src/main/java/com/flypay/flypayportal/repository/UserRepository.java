package com.flypay.flypayportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flypay.flypayportal.dao.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
