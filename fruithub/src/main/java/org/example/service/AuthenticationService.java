package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.LoginDto;
import org.example.dto.RegisterDto;
import org.example.dto.TokenDto;
//import org.example.enums.Role;
//import org.example.enums.Status;
import org.example.model.User;
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

    public String register(RegisterDto registerDto) {
        Optional<User> existingUser = userRepository.findByEmail(registerDto.getEmail());
        if (existingUser.isPresent()) {
            return "Email already exists";
        }

        if (registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            return "Passwords do not match";
        }

        User user = User.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
//                .role(Role.USER)
//                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);
        return "Registration completed";
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