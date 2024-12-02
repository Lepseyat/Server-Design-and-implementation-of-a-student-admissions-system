package org.acme.resource;

public class TokenResponse {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    // Getter for the token
    public String getToken() {
        return token;
    }

    // Setter for the token (if needed)
    public void setToken(String token) {
        this.token = token;
    }
}
