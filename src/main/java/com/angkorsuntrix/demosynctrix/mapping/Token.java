package com.angkorsuntrix.demosynctrix.mapping;

public class Token {

    private String token;
    private Long expired;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpired() {
        return expired;
    }

    public void setExpired(Long expired) {
        this.expired = expired;
    }
}
