package com.ccz.votesystem.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ccz.votesystem.entity.User;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    public String getToken(User user){
        return JWT.create().withAudience(String.valueOf(user.getUserId())).sign(Algorithm.HMAC256(user.getPassword()));
    }
}
