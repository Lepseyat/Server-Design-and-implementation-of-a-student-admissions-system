package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped; // For CDI-managed bean
import jakarta.inject.Inject; // For dependency injection
import org.acme.entity.Exam; // Replace with the correct package for your model
import org.acme.entity.ExamStatus;

import java.time.LocalDateTime;

import org.acme.dto.ExamRequest; // Replace with the correct package for your DTO
import org.acme.repository.ExamRepository; // Replace with the correct package for your repository

import jakarta.transaction.Transactional;

@ApplicationScoped  // Ensure this is a CDI-managed bean
public class ExamService {

    @Inject
    private ExamRepository examRepository;  // Inject ExamRepository

    @Transactional
    public void scheduleExam(ExamRequest request) {
    Exam exam = new Exam();

    // Set fields from request
    exam.setCandidateId(request.getCandidateId());
    exam.setExamDateTime(LocalDateTime.parse(request.getExamDateTime()));
    exam.setSubject(request.getSubject());
    exam.setStatus(ExamStatus.PENDING);

    // Persist the exam entity
    examRepository.persist(exam);
}


}
