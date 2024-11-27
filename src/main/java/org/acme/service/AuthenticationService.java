package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Candidate;

@ApplicationScoped
public class AuthenticationService {

    public boolean authenticate(String username, String password) {
        Candidate candidate = Candidate.find("username", username).firstResult();
        if (candidate != null && candidate.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
