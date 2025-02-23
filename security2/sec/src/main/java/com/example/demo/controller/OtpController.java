package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.payload.JwtTokenDto;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.JwtService;
import com.example.demo.service.OtpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {

    private final OtpService otpService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public OtpController(OtpService otpService, JwtService jwtService,
                         UserRepository userRepository) {
        this.otpService = otpService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/generate")
    public String generateOtp(
            @RequestParam String mobileNumber
    ){
        String otp = otpService.generateOtp(mobileNumber);
        return otp + "  " + mobileNumber;

    }

    @PostMapping("/validate")
    public String validateOtp(
            @RequestParam String otp,
            @RequestParam String mobileNumber
    ){
        boolean Status = otpService.validateOtp(otp,mobileNumber);

        if(Status){
            Optional<User> opUser = userRepository.findByMobileNumber(mobileNumber);
            if(opUser.isPresent()) {
                User user = opUser.get();
                return jwtService.generateToken(user.getUserName());
            }else{
                return "no such user with mobile number found";
            }

        }

        return "try again";
    }
}
