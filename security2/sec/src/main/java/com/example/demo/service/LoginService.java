package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.payload.LoginDto;
import com.example.demo.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class LoginService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public LoginService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String loginStatus(
            LoginDto dto
    ) throws UnsupportedEncodingException {
       Optional<User> opuser = userRepository.findByUserName(dto.getUserName());
       if(opuser.isPresent()){
           User user = opuser.get();
           if(BCrypt.checkpw(dto.getPassword(),user.getPassword())){
              return jwtService.generateToken(user.getUserName());
           }
       }
       return null;
    }
}
