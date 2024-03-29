package com.angkorsuntrix.demosynctrix.payload;

public class AccessToken {

    private String token;
    private Long expired;
    private String type = "Bearer";

    public AccessToken(String token, Long expired) {
        this.token = token;
        this.expired = expired;
    }

    public AccessToken(String token) {
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
