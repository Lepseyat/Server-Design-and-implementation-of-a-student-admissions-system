package org.acme.service;

import org.acme.entity.Candidate;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import io.smallrye.jwt.build.Jwt;
import java.util.List;

@ApplicationScoped
public class AuthenticationService {

    @Inject
    JsonWebToken jwt;

    // Method to authenticate and generate a JWT token
    public String authenticateAndGenerateToken(String username, String password) {
        Candidate candidate = Candidate.find("username", username).firstResult();
        
        if (candidate != null && candidate.getPassword().equals(password)) {
            // Generate JWT token for authenticated user
            return Jwt.issuer("http://localhost:8080")
        .subject(candidate.getId().toString()) // Set user ID as the subject
        .claim("username", candidate.getUsername())
        .claim("roles", List.of("USER"))
        .expiresIn(Duration.ofHours(12))
        .sign();

        }
        return null;  // Return null if authentication fails
    }
}

