package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.OtpVerification;
import org.example.repository.OTPRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final OTPRepository otpRepository;

    public String generateOTP(String email) {

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        OtpVerification otpVerification = OtpVerification.builder()
                .email(email)
                .otpCode(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .iUsed(false)
                .build();
        otpRepository.save(otpVerification);

        return otp;
    }

    public boolean validateOTP(String email, String otpCode) {
        return otpRepository.findByEmailAndOtpCode(email, otpCode)
                .filter(o -> o.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(o -> {
                    o.setIUsed(true);
                    otpRepository.save(o);
                    return true;
                })
                .orElse(false);
    }


}
