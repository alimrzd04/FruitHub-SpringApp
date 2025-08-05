package org.example.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private String confirmPassword;
    private boolean remember;
}