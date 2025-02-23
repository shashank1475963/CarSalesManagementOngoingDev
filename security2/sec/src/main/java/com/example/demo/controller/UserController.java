package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.payload.JwtTokenDto;
import com.example.demo.payload.LoginDto;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.LoginService;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

        private LoginService loginService;
        private UserRepository userRepository;
        private PasswordEncoder passwordEncoder;
    public UserController(LoginService loginService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.loginService = loginService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user-signup")
        public ResponseEntity<?> createUser(
                @RequestBody User user
        ){
        Optional<User> opUserName = userRepository.findByUserName(user.getUserName());
        if(opUserName.isPresent()){
            return new ResponseEntity<>("user already exists!",HttpStatus.IM_USED);
        }

        Optional<User> opEmailId = userRepository.findByEmailId(user.getEmailId());
        if(opEmailId.isPresent()){
            return new ResponseEntity<>("email already present",HttpStatus.IM_USED);
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return new ResponseEntity<>("CREATED",HttpStatus.CREATED);
    }

    @PostMapping("/contentManager-signup")
    public ResponseEntity<?> createContentManager(
            @RequestBody User user
    ){
        Optional<User> opUserName = userRepository.findByUserName(user.getUserName());
        if(opUserName.isPresent()){
            return new ResponseEntity<>("user already exists!",HttpStatus.IM_USED);
        }

        Optional<User> opEmailId = userRepository.findByEmailId(user.getEmailId());
        if(opEmailId.isPresent()){
            return new ResponseEntity<>("email already present",HttpStatus.IM_USED);
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_CONTENTMANAGER");
        userRepository.save(user);
        return new ResponseEntity<>("CREATED",HttpStatus.CREATED);
    }

    @PostMapping("/blogManager-signup")
    public ResponseEntity<?> createBlogManager(
            @RequestBody User user
    ){
        Optional<User> opUserName = userRepository.findByUserName(user.getUserName());
        if(opUserName.isPresent()){
            return new ResponseEntity<>("user already exists!",HttpStatus.IM_USED);
        }

        Optional<User> opEmailId = userRepository.findByEmailId(user.getEmailId());
        if(opEmailId.isPresent()){
            return new ResponseEntity<>("email already present",HttpStatus.IM_USED);
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_BLOGMANAGER");
        userRepository.save(user);
        return new ResponseEntity<>("CREATED",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDto dto
    ) throws UnsupportedEncodingException {
        String jwtToken = loginService.loginStatus(dto);

        if(jwtToken!=null){
            JwtTokenDto jwtTokenDto=new JwtTokenDto();
            jwtTokenDto.setToken(jwtToken);
            jwtTokenDto.setTokenType("JWT");
            return new ResponseEntity<>(jwtTokenDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("invalid",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/message")
    public String getMessage(){
        return "hello";
    }
}
