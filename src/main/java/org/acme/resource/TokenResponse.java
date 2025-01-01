package org.acme.resource;

public class TokenResponse {
    private String token;
    private boolean isAdmin;

    // Constructor accepting both token and isAdmin
    public TokenResponse(String token, boolean isAdmin) {
        this.token = token;
        this.isAdmin = isAdmin;
    }

    // Getters and setters for token and isAdmin
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}