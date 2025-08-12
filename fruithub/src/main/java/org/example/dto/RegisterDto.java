package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDto {

    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Düzgün email formatı daxil edin")
    private String email;
    private String password;
    private String confirmPassword;
    private boolean remember;
}