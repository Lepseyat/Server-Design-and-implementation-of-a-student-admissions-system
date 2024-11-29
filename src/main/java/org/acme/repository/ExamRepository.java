package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped; // For CDI-managed bean
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.acme.entity.Exam;

@ApplicationScoped // CDI managed bean
public class ExamRepository {

    @PersistenceContext
    private EntityManager entityManager;  // Injected EntityManager for database operations

    public void persist(Exam exam) {
        entityManager.persist(exam);  // Persist the exam entity to the database
    }
}

