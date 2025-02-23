package com.example.demo.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;



@Service
public class JwtService {

    private Algorithm algorithm;

    @Value("${jwt.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiry;

    @PostConstruct
    public void pc() throws UnsupportedEncodingException {
        algorithm=Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(String username){
        return JWT.create()
                .withClaim("username",username)
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis()+expiry))
                .sign(algorithm);
    }

    public String getUsername(String token) {
        DecodedJWT dj = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
        return dj.getClaim("username").asString();

    }

}

