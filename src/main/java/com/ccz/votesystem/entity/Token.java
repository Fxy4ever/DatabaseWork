package com.ccz.votesystem.entity;

import lombok.Data;

@Data
public class Token {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
