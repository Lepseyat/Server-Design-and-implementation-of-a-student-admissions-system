package org.acme.service;

import org.acme.entity.Candidate;
import org.acme.util.PasswordUtil;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class AuthenticationService {

    @Inject
    JsonWebToken jwt;  // Inject JsonWebToken (if needed elsewhere)

    @Inject
    JWTParser jwtParser;  // Inject JWTParser to parse tokens

// Method to authenticate and generate a JWT token
public String authenticateAndGenerateToken(String username, String password) {
    Candidate candidate = Candidate.find("username", username).firstResult();

    if (candidate == null) {
        System.out.println("No user found with username: " + username);
        return null;  // Username not found
    }

    System.out.println("User found: " + candidate.getUsername());
    System.out.println("Stored hashed password: " + candidate.getPassword());
    System.out.println("Raw password provided: " + password);

    boolean passwordMatches = PasswordUtil.verifyPassword(password, candidate.getPassword());
    System.out.println("Password matches: " + passwordMatches);

    if (passwordMatches) {
        System.out.println("Admin status in DB: " + candidate.getAdmin());
        String role = Boolean.TRUE.equals(candidate.getAdmin()) ? "ADMIN" : "USER";

        return Jwt.issuer("http://localhost:8080")
                .subject(candidate.getId().toString())
                .claim("username", candidate.getUsername())
                .claim("role", role)
                .expiresIn(Duration.ofHours(12))
                .sign();
    }

    return null;  // Password mismatch
}

    // Method to parse the JWT token
    public JsonWebToken parseToken(String token) {
        try {
            return jwtParser.parse(token);  // Parse the token and return
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse token", e);  // Handle any parsing errors
        }
    }
}

