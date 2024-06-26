package com.wevioo.cantine.security.payloads.response;

import java.util.List;

public class JwtResponse implements AuthApiResponse {
    private String token;
    private String type = "Bearer ";
    private Long id;
    private String username;

    private Boolean isEnabled;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, Boolean isEnabled, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public void setMessage(String message) {
        //Empty, there is nothing to implement
    }
}

