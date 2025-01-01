package org.acme.service;

import org.acme.entity.Candidate;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import java.util.List;

@ApplicationScoped
public class AuthenticationService {

    @Inject
    JsonWebToken jwt;  // Inject JsonWebToken (if needed elsewhere)

    @Inject
    JWTParser jwtParser;  // Inject JWTParser to parse tokens

// Method to authenticate and generate a JWT token
public String authenticateAndGenerateToken(String username, String password) {
    Candidate candidate = Candidate.find("username", username).firstResult();

    if (candidate != null && candidate.getPassword().equals(password)) {
        System.out.println("Admin status in DB: " + candidate.getAdmin());
        
        // Determine the role based on the boolean column
        String role = Boolean.TRUE.equals(candidate.getAdmin()) ? "ADMIN" : "USER";

        // Generate JWT token for authenticated user
        return Jwt.issuer("http://localhost:8080")
                .subject(candidate.getId().toString()) // Set user ID as the subject
                .claim("username", candidate.getUsername())
                .claim("role", role) // Use a single role as a string
                .expiresIn(Duration.ofHours(12))
                .sign();
    }

    return null;  // Return null if authentication fails
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

