package org.acme.service;

import org.acme.dto.CandidateDTO;
import org.acme.entity.Candidate;
import org.acme.repository.CandidateRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CandidateService {

    @Inject
    CandidateRepository candidateRepository;

    public List<CandidateDTO> getAllCandidates() {
        return candidateRepository.listAll().stream()
                .map(candidate -> new CandidateDTO(
                        candidate.getId(),
                        candidate.getName(),
                        candidate.getSurname(),
                        candidate.getLastName(),
                        candidate.getEmail(),
                        candidate.getPhone()
                ))
                .collect(Collectors.toList());
    }
}