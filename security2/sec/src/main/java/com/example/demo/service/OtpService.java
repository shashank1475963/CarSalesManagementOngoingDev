package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.payload.OtpDetails;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    private final Map<String, OtpDetails> otpStorage = new HashMap<>();
    private static final int OTP_EXPIRY_TIME = 5;
    private final UserRepository userRepository;

    public OtpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateOtp(String mobileNumber){
        Optional<User> opUser = userRepository.findByMobileNumber(mobileNumber);
        if(opUser.isPresent()){
            String otp = String.format("%06d", new Random().nextInt(999999));
            OtpDetails otpDetails = new OtpDetails(otp,System.currentTimeMillis());
            otpStorage.put(mobileNumber,otpDetails);
            return otp;
        }

        return "the mobile number you entered is wrong, try again";

    }

    public boolean validateOtp(String otp, String mobileNumber){
        OtpDetails otpDetails = otpStorage.get(mobileNumber);
        long timeDiff = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - otpDetails.getTimeStamp());
        if(timeDiff > OTP_EXPIRY_TIME){

            //otp expired
            return false;

        }

        return otp.equals(otpDetails.getOtp());
    }
}
