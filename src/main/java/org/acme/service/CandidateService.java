package org.acme.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.CandidateDTO;
import org.acme.entity.Candidate;
import org.acme.repository.CandidateRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CandidateService {

    @Inject
    CandidateRepository candidateRepository;

    @Inject
    ReactiveMailer mailer;

    public List<CandidateDTO> getAllCandidates() {
        return candidateRepository.listAll().stream()
                .map(candidate -> new CandidateDTO(
                        candidate.getId(),
                        candidate.getName(),
                        candidate.getSurname(),
                        candidate.getLastName(),
                        candidate.getEmail(),
                        candidate.getEgn(),
                        candidate.getPhone()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(CandidateDTO candidateDTO) {
        // Map CandidateDTO to Candidate entity
        Candidate candidate = new Candidate();
        candidate.setName(candidateDTO.getName());
        candidate.setSurname(candidateDTO.getSurname());
        candidate.setLastName(candidateDTO.getLastname());
        candidate.setEgn(candidateDTO.getEgn());
        candidate.setPhone(candidateDTO.getPhone());
        candidate.setEmail(candidateDTO.getEmail());

        String username = candidateDTO.getEmail();
        String generatedPassword = "RandomGenerated123"; // Replace with a secure password generator
        candidate.setUsername(username);
        candidate.setPassword(generatedPassword); // Assume there's a setPassword() in Candidate entity

        // Persist the Candidate entity
        candidate.persist();

        // Send a welcome email
        sendWelcomeEmail(candidate.getEmail(), candidate.getName(), generatedPassword);
    }

    private void sendWelcomeEmail(String email, String name, String password) {
        String body = String.format(
                """
                <html>
                <body>
                    <h3>Welcome, %s!</h3>
                    <p>You have successfully registered.</p>
                    <p>Your login details are as follows:</p>
                    <p><strong>Username:</strong> %s</p>
                    <p><strong>Password:</strong> %s</p>
                    <p>Please keep this information secure.</p>
                    <p>Best regards,</p>
                    <p>The Team</p>
                </body>
                </html>
                """, name, email, password);
    
        // Log the email sending attempt
        System.out.println("Attempting to send email to: " + email);
    
        mailer.send(Mail.withHtml(email, "Welcome to the Platform!", body))
                .subscribe()
                .with(
                        success -> System.out.println("Email sent successfully to " + email),
                        failure -> System.err.println("Failed to send email: " + failure.getMessage())
                );
    }
    
}


