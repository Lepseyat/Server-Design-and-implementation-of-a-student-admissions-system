package org.acme.service;

import org.acme.entity.Candidate;
import org.acme.util.PasswordUtil;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.LocalDateTime;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;

@ApplicationScoped
public class AuthenticationService {

    @Inject
    JsonWebToken jwt;

    @Inject
    JWTParser jwtParser;

    @Inject
    ReactiveMailer mailer;

public String authenticateAndGenerateToken(String username, String password) {
    Candidate candidate = Candidate.find("username", username).firstResult();

    if (candidate == null) {
        System.out.println("No user found with username: " + username);
        return null;
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

    return null; 
}


    public JsonWebToken parseToken(String token) {
        try {
            return jwtParser.parse(token);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse token", e);
        }
    }

    @Transactional
    public void requestPasswordReset(String email) {
        Candidate candidate = Candidate.find("email", email).firstResult();
    
        if (candidate == null) {

            System.out.println("Password reset requested for unknown email: " + email);
            return;
        }
    
        String token = java.util.UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusHours(1);
    
        candidate.resetToken = token;
        candidate.resetTokenExpiry = expiry;
        candidate.persist();
    
        String resetLink = "http://localhost:3000/reset-password?token=" + token;
    
        String body = String.format("""
            <html>
            <body>
                <h3>Password Reset Request</h3>
                <p>Hello %s,</p>
                <p>Click the link below to reset your password:</p>
                <p><a href="%s">Reset Password</a></p>
                <p>This link will expire in 1 hour.</p>
            </body>
            </html>
            """, candidate.getName(), resetLink);
    
        mailer.send(Mail.withHtml(email, "Reset Your Password", body))
            .subscribe()
            .with(
                success -> System.out.println("Reset email sent to: " + email),
                failure -> System.err.println("Failed to send reset email: " + failure.getMessage())
            );
    }
    

    @Transactional
    public boolean resetPassword(String token, String newPassword) {
    Candidate candidate = Candidate.find("resetToken", token).firstResult();

    if (candidate == null || candidate.resetTokenExpiry == null || candidate.resetTokenExpiry.isBefore(LocalDateTime.now())) {
        System.out.println("Invalid or expired reset token.");
        return false;
    }

    String hashedPassword = PasswordUtil.encodePassword(newPassword);

    candidate.setPassword(hashedPassword);
    candidate.resetToken = null;
    candidate.resetTokenExpiry = null;
    candidate.persist();

    System.out.println("Password reset successful for user: " + candidate.getUsername());
    return true;
}


    

}

