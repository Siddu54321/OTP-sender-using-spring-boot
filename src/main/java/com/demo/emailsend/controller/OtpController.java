package com.demo.emailsend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.demo.emailsend.service.EmailService;
import com.demo.emailsend.service.OtpService;

@RestController
public class OtpController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    private String lastEmail; // Store the last email for OTP generation

    @GetMapping("/sendOtp")
    public String sendOtp(@RequestParam String to) {
        try {
            lastEmail = to; 
            String otp = otpService.generateOtp(to);
            emailService.sendEmail(to, "Your OTP Code", "Your OTP is: " + otp);
            return "OTP sent successfully!";
        } catch (Exception e) {
            return "Error sending OTP: " + e.getMessage();
        }
    }


    @PostMapping("/validateOtp")
    public String validateOtp(@RequestParam String otp) {
        boolean isValid = otpService.validateOtp(lastEmail, otp);
        if (isValid) {
            otpService.clearOtp(lastEmail); // Clear OTP after successful validation
            return "OTP is valid!";
        } else {
            return "Invalid OTP!";
        }
    }
}