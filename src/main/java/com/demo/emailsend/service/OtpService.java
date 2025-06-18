package com.demo.emailsend.service;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;


@Service
public class OtpService {

    private final SecureRandom secureRandom = new SecureRandom();
    private final Map<String, String> otpStore = new HashMap<>(); // Store OTPs temporarily

    public String generateOtp(String email) {
        int otp = 100000 + secureRandom.nextInt(900000); // Generates a 6-digit OTP
        otpStore.put(email, String.valueOf(otp));
        System.out.println("Generated OTP for " + email + ": " + otp);
        return String.valueOf(otp);
    }

    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);
        System.out.println("Validating OTP for " + email + ": " + otp + " (Stored: " + storedOtp + ")");
        return storedOtp != null && storedOtp.equals(otp);
    }

    public void clearOtp(String email) {
        otpStore.remove(email); // Clear OTP after validation
    }
}