package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.LoginDto;
import org.example.dto.RegisterDto;
import org.example.dto.TokenDto;
import org.example.model.Status;
import org.example.model.User;
import org.example.repository.StatusRepository;
import org.example.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final StatusRepository statusRepository;

    public RegisterDto register(RegisterDto registerDto) {
        Optional<User> existingUser = userRepository.findByEmail(registerDto.getEmail());
        if (existingUser.isPresent()) {
            return RegisterDto.builder()
                    .message("Email already exists")
                    .email(registerDto.getEmail())
                    .build();
        }

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            return RegisterDto.builder()
                    .message("Passwords do not match")
                    .email(registerDto.getEmail())
                    .build();
        }

        Status activeStatus = statusRepository.findByName("ACTIVE")
                .orElseThrow(() -> new RuntimeException("Default status not found"));

        User user = User.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .status(activeStatus)
                .build();


        userRepository.save(user);

        System.out.println("User registered: " + user.getEmail() +
                ", Status: " + user.getStatus().getName() +
                ", Enabled: " + user.isEnabled());

        return RegisterDto.builder()
                .message("Register completed")
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .confirmPassword(registerDto.getConfirmPassword())
                .remember(registerDto.isRemember())
                .build();

    }

    public TokenDto login(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail_address(),
                            loginDto.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginDto.getEmail_address())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtService.generateToken(user);

            return TokenDto.builder()
                    .token(token)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password: " + e.getMessage());
        }
    }
}