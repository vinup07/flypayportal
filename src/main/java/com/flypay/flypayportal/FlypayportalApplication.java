package com.flypay.flypayportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class FlypayportalApplication {

	public static void main(String[] args) {
		log.info("Starting Flypay Portal Application");
		SpringApplication.run(FlypayportalApplication.class, args);
	}

}
