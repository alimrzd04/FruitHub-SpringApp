package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.example.validation.Password;

@Data
@Builder
public class RegisterDto {

    String message;
    @NotEmpty(message = "Email boş ola bilməz")
    @Email(message = "Email düzgün deyil")
    private String email;
    @Password(message = "Sifre bos ve ya 6 simvoldan az ola bilməz")
    private String password;
    @NotBlank(message = "Təkrar şifrə boş ola bilməz")
    private String confirmPassword;
    private boolean remember;
}