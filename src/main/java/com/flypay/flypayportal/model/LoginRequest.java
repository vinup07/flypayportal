package com.flypay.flypayportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @Schema(description = "The email of the user", example = "kaushik@gmail.com", required = true)
    @NotBlank
    private String email;

    @Schema(description = "The password of the user", example = "password", required = true)
    @NotBlank
    private String password;

    // Getters and Setters
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
}
