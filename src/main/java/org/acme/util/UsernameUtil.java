package org.acme.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.repository.CandidateRepository;

@ApplicationScoped
public class UsernameUtil {

    @Inject
    CandidateRepository candidateRepository;

    public String generateUniqueUsername() {
        String yearPrefix = String.valueOf(java.time.Year.now().getValue()).substring(2);
        String username;
        int attempts = 0;
        do {
            if (++attempts > 50) {
                throw new IllegalStateException("Unable to generate unique username after 50 attempts.");
            }
            int randomNumber = (int)(Math.random() * 9000) + 1000;
            username = yearPrefix + randomNumber;
        } while (candidateRepository.find("username", username).firstResult() != null);
        return username;
    }
}