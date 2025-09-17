package org.example.repository;

import java.util.Optional;
import org.example.model.OtpVerification;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface OTPRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findByEmailAndOtpCode(String email, String otpCode);

}