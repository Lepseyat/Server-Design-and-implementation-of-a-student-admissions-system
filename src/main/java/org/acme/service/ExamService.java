package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.SecurityContext;
import org.acme.dto.ExamDTO;
import org.acme.entity.Exam;
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
    public void scheduleExam(ExamDTO request) {
        Exam exam = new Exam();

        try {
            // Parse the exam date-time from the request
            exam.setExamDateTime(LocalDateTime.parse(request.getExamDateTime()));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date-time format: " + request.getExamDateTime(), e);
        }

        exam.setSubject(request.getSubject());
        

        examRepository.persist(exam);
    }
}
