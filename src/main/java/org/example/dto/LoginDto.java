package org.example.dto;


import lombok.Data;

@Data
public class LoginDto {
    private String email_address;
    private String password;
    private boolean remember;
}
