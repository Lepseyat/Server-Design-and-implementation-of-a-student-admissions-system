package org.acme.service;

import org.acme.dto.CandidateDTO;
import org.acme.entity.Candidate;
import org.acme.repository.CandidateRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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
        candidate.setLastName(candidateDTO.getLastName());
        candidate.setEgn(candidateDTO.getEgn());
        candidate.setPhone(candidateDTO.getPhone());
        candidate.setEmail(candidateDTO.getEmail());

        // Persist the Candidate entity
        candidate.persist();
    }
}