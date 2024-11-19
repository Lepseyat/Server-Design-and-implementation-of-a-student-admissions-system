package org.acme.repository;

import org.acme.entity.Candidate;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CandidateRepository implements PanacheRepository<Candidate> {
    // Panache provides basic methods like find, persist, delete, etc.
}