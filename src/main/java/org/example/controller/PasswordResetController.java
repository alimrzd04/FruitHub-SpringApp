package org.example.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.example.dto.ForgotPasswordRequest;
import org.example.model.Users;
import org.example.service.JwtService;
import org.example.service.OTPService;
import org.example.dto.VerifyOtpRequest;
import org.springframework.http.HttpStatus;
import org.example.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PasswordResetController {
    private final OTPService otpService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        otpService.generateOTP(request.getEmail());
        return ResponseEntity.ok("OTP email ünvanınıza göndərildi");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody VerifyOtpRequest request) {
        if (otpService.validateOTP(request.getEmail(), request.getOtp())) {
            String resetToken = jwtService.gererateReSetToken(request.getEmail());
            return ResponseEntity.ok(Map.of("resetToken", resetToken));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "OTP səhvdir və ya vaxtı bitib"));
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestHeader("Authorization") String token,
                                                @RequestBody Map<String, String> request) {
        String jwt = token.substring(7);
        String email = jwtService.findUsername(jwt);

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!request.get("password").equals(request.get("confirmPassword"))) {
            return ResponseEntity.badRequest().body("Parollar uyğun gəlmir");
        }

        user.setPassword(passwordEncoder.encode(request.get("password")));
        userRepository.save(user);

        return ResponseEntity.ok("Parol uğurla dəyişdirildi");
    }
}



