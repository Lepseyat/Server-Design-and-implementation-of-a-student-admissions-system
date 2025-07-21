package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.entity.ExamRequest;
import org.acme.entity.ExamStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.acme.dto.ExamRequestDTO;
import java.util.List;

@ApplicationScoped
public class ExamRequestRepository implements PanacheRepository<ExamRequest> {

    @PersistenceContext
EntityManager entityManager;

public List<ExamRequestDTO> findPendingRequests() {
    return entityManager.createQuery(
        "SELECT new org.acme.dto.ExamRequestDTO(er.id, er.candidate.name, er.exam.subject, er.status, er.receipt) " +
        "FROM ExamRequest er " +
        "WHERE er.status = :status", ExamRequestDTO.class)
    .setParameter("status", ExamStatus.PENDING)
    .getResultList();
}

    public void persistExamRequest(ExamRequest examRequest) {
        persist(examRequest);
    }

    public ExamRequest findById(Long id) {
        return find("id", id).firstResult(); 
    }
}
