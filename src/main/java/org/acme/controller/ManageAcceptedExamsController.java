package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.dto.ExamRequestViewDTO;
import org.acme.dto.RankingDTO;
import org.acme.entity.ExamRequest;
import org.acme.entity.ExamStatus;
import org.acme.repository.ExamRequestRepository;
import jakarta.persistence.EntityManager;

import org.acme.entity.Candidate;
import org.acme.entity.Exam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/exam/manage")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ManageAcceptedExamsController {

    @Inject
    ExamRequestRepository examRequestRepository;

    @Inject
    EntityManager entityManager;

    @Inject
    ExamRequestRepository examRequestRepo;

@GET
@Path("/accepted")
@Produces(MediaType.APPLICATION_JSON)
public Response getAcceptedExamRequests() {
    List<ExamRequest> requests = examRequestRepository
        .list("status IN (?1, ?2)", ExamStatus.ACCEPTED, ExamStatus.COMPLETED);

    List<ExamRequestViewDTO> dtoList = requests.stream()
        .map(ExamRequestViewDTO::new)
        .collect(Collectors.toList());

    return Response.ok(dtoList).build();
}


@PUT
@Path("/{id}/grade")
@Transactional
public Response updateExamResult(@PathParam("id") Long id, ExamRequest updatedData) {
    Optional<ExamRequest> optionalRequest = examRequestRepository.findByIdOptional(id);

    if (optionalRequest.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Exam request not found with ID: " + id)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    ExamRequest existing = optionalRequest.get();

    if (updatedData.getExamScore() != null) {
        existing.setExamScore(updatedData.getExamScore());
    }

    if (updatedData.getExamGrade() != null) {
        existing.setExamGrade(updatedData.getExamGrade());
    }

    if (updatedData.getStatus() != null) {
        existing.setStatus(updatedData.getStatus());
    }

    if (updatedData.getAverageDiplomaDegree() != null) {
        existing.setAverageDiplomaDegree(updatedData.getAverageDiplomaDegree());
    }

    if (updatedData.getSubjectDegree() != null) {
        existing.setSubjectDegree(updatedData.getSubjectDegree());
    }

    
    if (existing.getExamGrade() != null &&
        existing.getAverageDiplomaDegree() != null &&
        existing.getSubjectDegree() != null) {

       float bal = (float)(existing.getExamGrade() * 3
          + existing.getAverageDiplomaDegree()
          + existing.getSubjectDegree());


        existing.setBal(bal);
    }

    examRequestRepository.persist(existing);

    return Response.ok(existing).build();
}

// Gets all specialties from the database
@GET
@Path("/specialties")
public Response getAllSpecialties() {
    List<String> specialties = entityManager
        .createQuery("SELECT DISTINCT e.specialty FROM Exam e", String.class)
        .getResultList();
    return Response.ok(specialties).build();
}

// Gets all exams for a specific specialty
@GET
@Path("/exams/{specialty}")
public Response getExamsBySpecialty(@PathParam("specialty") String specialty) {
    List<Exam> exams = entityManager.createQuery(
        "SELECT e FROM Exam e WHERE e.specialty = :specialty", Exam.class)
        .setParameter("specialty", specialty)
        .getResultList();
    return Response.ok(exams).build();
}

// Shows the ranking of candidates for a specific specialty and exam
@GET
@Path("/ranking/{specialty}/{examId}")
@Produces(MediaType.APPLICATION_JSON)
public List<RankingDTO> getRankingBySpecialtyAndExam(
        @PathParam("specialty") String specialty,
        @PathParam("examId") Long examId) {
    return examRequestRepo.find(
            "SELECT er FROM ExamRequest er WHERE er.exam.specialty = ?1 AND er.exam.id = ?2 AND er.status = ?3",
            specialty, examId, ExamStatus.COMPLETED)
        .stream()
        .sorted((a, b) -> {
            float balA = (float)(a.getExamGrade() * 3 + a.getAverageDiplomaDegree() + a.getSubjectDegree());
            float balB = (float)(b.getExamGrade() * 3 + b.getAverageDiplomaDegree() + b.getSubjectDegree());
            return Float.compare(balB, balA);
        })
        .map(er -> {
            Candidate c = er.getCandidate();
            String fullName = c != null
                ? c.getName() + " " + c.getSurname() + " " + c.getLastName()
                : "Unknown";
            return new RankingDTO(
                fullName,
                er.getExamGrade(),
                er.getAverageDiplomaDegree(),
                er.getSubjectDegree(),
                er.getBal());
        })
        .collect(Collectors.toList());
}

}
