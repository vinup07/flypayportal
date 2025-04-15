package com.flypay.flypayportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

	@Schema(description = "The email of the user", example = "kaushik@gmail.com", required = true)
	@NotBlank
	@Email(message = "Email should be valid")
	private String email;

	@Schema(description = "The password of the user", example = "password", required = true)
	@NotBlank
	private String password;
	
	@Enumerated(value = EnumType.STRING)
    private Role role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
