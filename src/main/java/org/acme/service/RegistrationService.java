package org.acme.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.CandidateDTO;
import org.acme.entity.Candidate;
import org.acme.repository.CandidateRepository;
import org.acme.util.PasswordUtil;
import org.acme.util.UsernameUtil;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RegistrationService {

    @Inject
    UsernameUtil usernameUtil;

    @Inject
    CandidateRepository candidateRepository;

    @Inject
    ReactiveMailer mailer;

    public List<CandidateDTO> getAllCandidates() {
        return candidateRepository.listAll().stream()
                .map(candidate -> mapToDTO(candidate))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(CandidateDTO candidateDTO) {

    Candidate candidate = mapToEntity(candidateDTO);

    String rawPassword = PasswordUtil.generateRandomPassword();

    String hashedPassword = PasswordUtil.encodePassword(rawPassword);

    String username = usernameUtil.generateUniqueUsername();
    candidate.setUsername(username);
    candidate.setPassword(hashedPassword);

    candidate.persist();


    sendWelcomeEmail(candidate.getEmail(), candidate.getName(), username, rawPassword);
}

    private void sendWelcomeEmail(String email, String name, String username, String password) {
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
        """, name, username, password);

    System.out.println("Attempting to send email to: " + email);

    mailer.send(Mail.withHtml(email, "Welcome to the Platform!", body))
        .subscribe()
        .with(
            success -> System.out.println("Email sent successfully to " + email),
            failure -> System.err.println("Failed to send email: " + failure.getMessage())
        );
}
    
    private CandidateDTO mapToDTO(Candidate candidate) {
        CandidateDTO dto = new CandidateDTO();
        dto.setId(candidate.getId());
        dto.setName(candidate.getName());
        dto.setSurname(candidate.getSurname());
        dto.setLastName(candidate.getLastName());
        dto.setEmail(candidate.getEmail());
        dto.setEgn(candidate.getEgn());
        dto.setPhone(candidate.getPhone());
        dto.setUsername(candidate.getUsername());
        dto.setLatinName(candidate.getLatinName());
        dto.setLatinSurname(candidate.getLatinSurname());
        dto.setLatinLastname(candidate.getLatinLastname());
        dto.setDateOfBirth(candidate.getDateOfBirth());
        dto.setPlaceOfBirth(candidate.getPlaceOfBirth());
        dto.setIdNumber(candidate.getIdNumber());
        dto.setDateIdCreated(candidate.getDateIdCreated());
        dto.setIdIssuedBy(candidate.getIdIssuedBy());
        dto.setAddress(candidate.getAddress());
        dto.setCity(candidate.getCity());
        dto.setMunicipality(candidate.getMunicipality());
        dto.setDistrict(candidate.getDistrict());
        dto.setSchoolName(candidate.getSchoolName());
        dto.setSchoolCity(candidate.getSchoolCity());
        dto.setSecondaryEducation(candidate.getSecondaryEducation());
        dto.setAdmin(candidate.getAdmin());
        return dto;
    }

    private Candidate mapToEntity(CandidateDTO dto) {
        Candidate candidate = new Candidate();
        candidate.setName(dto.getName());
        candidate.setSurname(dto.getSurname());
        candidate.setLastName(dto.getLastName());
        candidate.setEmail(dto.getEmail());
        candidate.setEgn(dto.getEgn());
        candidate.setPhone(dto.getPhone());
        candidate.setLatinName(dto.getLatinName());
        candidate.setLatinSurname(dto.getLatinSurname());
        candidate.setLatinLastname(dto.getLatinLastname());
        candidate.setDateOfBirth(dto.getDateOfBirth());
        candidate.setPlaceOfBirth(dto.getPlaceOfBirth());
        candidate.setIdNumber(dto.getIdNumber());
        candidate.setdDateIdCreated(dto.getDateIdCreated());
        candidate.setIdIssuedBy(dto.getIdIssuedBy());
        candidate.setAddress(dto.getAddress());
        candidate.setCity(dto.getCity());
        candidate.setMunicipality(dto.getMunicipality());
        candidate.setDistrict(dto.getDistrict());
        candidate.setSchoolName(dto.getSchoolName());
        candidate.setSchoolCity(dto.getSchoolCity());
        candidate.setSecondaryEducation(dto.getSecondaryEducation());
        candidate.setAdmin(dto.getAdmin());
        return candidate;
    }

    public CandidateDTO getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id);
        if (candidate != null) {
            return mapToDTO(candidate);
        }
        return null;
    }
}
