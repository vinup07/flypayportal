package com.flypay.flypayportal.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Audit {

	LocalDateTime createdAt = LocalDateTime.now();
	
	LocalDateTime updatedAt = LocalDateTime.now();
	
}
