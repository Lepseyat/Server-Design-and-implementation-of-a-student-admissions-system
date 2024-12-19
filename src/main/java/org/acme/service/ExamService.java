package org.acme.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.SecurityContext;
import org.acme.dto.ExamRequestDTO;
import org.acme.entity.Exam;
import org.acme.entity.ExamStatus;
import org.acme.repository.ExamRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@ApplicationScoped
public class ExamService {

    @Inject
    private ExamRepository examRepository;

    @Inject
    SecurityContext securityContext;

    @Transactional
    public void scheduleExam(ExamRequestDTO request) {
        Exam exam = new Exam();

        // Extract candidate ID from the security context (JWT subject)
        String candidateId = securityContext.getUserPrincipal().getName();

        // Parse the candidateId to Long, assuming it's a numeric ID
        try {
            exam.setCandidateId(Long.parseLong(candidateId));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid candidate ID in JWT token", e);
        }

        // Set remaining fields
        try {
            // Parse the exam date-time from the request
            exam.setExamDateTime(LocalDateTime.parse(request.getExamDateTime()));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date-time format: " + request.getExamDateTime(), e);
        }

        // Set exam subject and status
        exam.setSubject(request.getSubject());
        exam.setStatus(ExamStatus.PENDING); // Default to PENDING status

        // Persist the exam entity to the database
        examRepository.persist(exam);
    }
}
