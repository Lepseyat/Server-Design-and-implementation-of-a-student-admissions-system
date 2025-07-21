package org.acme.controller;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.acme.repository.CandidateRepository;
import org.acme.repository.ExamRepository;
import org.acme.dto.ExamRequestViewDTO;
import org.acme.entity.Exam;
import org.acme.entity.ExamRequest;
import org.acme.entity.ExamStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.acme.repository.ExamRequestRepository;
import java.util.Optional;
import java.util.stream.Collectors;



@Path("/api/exam")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminExamController {

    @Inject
    ExamRepository examRepository;

    @Inject
    CandidateRepository candidateRepository;

    @Inject
    ExamRequestRepository examRequestRepository;

    @Inject
    EntityManager entityManager; 

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createExam(Exam exam) {
        if (exam.getSubject() == null || exam.getExamDateTime() == null || exam.getSpecialty() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Subject, Exam Date Time, and Specialty are required.")
                    .build();
        }

        LocalDateTime examDateTime = exam.getExamDateTime();
        exam.setExamDateTime(examDateTime);

        examRepository.persist(exam);
        return Response.ok(exam).build();
    }

    @GET
    @Path("/{id}/edit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExam(@PathParam("id") Long id) {
        Exam exam = examRepository.findById(id);
        if (exam == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Exam not found")
                    .build();
        }
        return Response.ok(exam).build();
    }

    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateExam(@PathParam("id") Long id, Exam updatedExam) {
        Exam exam = examRepository.findById(id);
        if (exam == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Exam not found")
                    .build();
        }
    
        exam.setExamDateTime(updatedExam.getExamDateTime());
        exam.setSubject(updatedExam.getSubject());
    
        examRepository.persist(exam);
        return Response.ok(exam).build();
    }

    @DELETE
    @Path("/{id}/delete")
    @Transactional
    public Response deleteExam(@PathParam("id") Long id) {
        Exam exam = examRepository.findById(id);
        if (exam == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Exam not found")
                    .build();
        }

        examRepository.delete(exam);
        return Response.ok("Exam deleted successfully").build();
    }

    @GET
    @Path("/list")
    public Response getAllExams() {
        List<Exam> exams = examRepository.listAll();
        return Response.ok(exams).build();
    }

    @GET
    @Path("/pendingRequests")
    public Response getPendingExamRequests() {
        List<ExamRequest> pendingRequests = examRequestRepository.find("status", ExamStatus.PENDING).list();
        
        List<ExamRequestViewDTO> dtoList = pendingRequests.stream()
                .map(ExamRequestViewDTO::new)
                .collect(Collectors.toList());
        return Response.ok(dtoList).build();
    }

    @GET
    @Path("/{id}/receipt")
    @Produces("image/png")
    public Response getReceipt(@PathParam("id") Long id) {
        Optional<ExamRequest> request = examRequestRepository.findByIdOptional(id);

        if (request.isEmpty() || request.get().getReceipt() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        byte[] imageData = request.get().getReceipt();
        return Response.ok(imageData).build();
    }

    @POST
    @Path("/{id}/accept")
    @Transactional
    public Response acceptExam(@PathParam("id") Long id) {
        ExamRequest examRequest = examRequestRepository.findById(id);
        if (examRequest == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Exam request not found")
                    .build();
        }
      
        if (examRequest.getStatus() == ExamStatus.PENDING) {
            examRequest.setStatus(ExamStatus.ACCEPTED);
            examRequestRepository.persist(examRequest);
            return Response.ok("Exam request accepted").build();
        }
      
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Exam request is not in a pending state")
                .build();
    }
  
    @POST
    @Path("/{id}/deny")
    @Transactional
    public Response denyExam(@PathParam("id") Long id) {
        ExamRequest examRequest = examRequestRepository.findById(id);
        if (examRequest == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Exam request not found")
                    .build();
        }
      
        if (examRequest.getStatus() == ExamStatus.PENDING) {
            examRequest.setStatus(ExamStatus.DENIED);
            examRequestRepository.persist(examRequest);
            return Response.ok("Exam request denied").build();
        }
      
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Exam request is not in a pending state")
                .build();
    }

}
  