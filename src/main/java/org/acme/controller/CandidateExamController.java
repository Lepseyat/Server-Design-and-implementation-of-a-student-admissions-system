package org.acme.controller;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.acme.repository.CandidateRepository;
import org.acme.repository.ExamRepository;
import org.acme.entity.Candidate;
import org.acme.entity.Exam;
import org.acme.entity.ExamRequest;
import org.acme.entity.ExamStatus;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.acme.repository.ExamRequestRepository;
import org.jboss.resteasy.reactive.MultipartForm;
import io.smallrye.common.annotation.Blocking;
import org.acme.dto.ExamRequestDTO;

@Path("/api/exam")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CandidateExamController{
    @Inject
    ExamRepository examRepository;

    @Inject
    CandidateRepository candidateRepository;

    @Inject
    ExamRequestRepository examRequestRepository;

    @Inject
    EntityManager entityManager; 

    @GET
    @Path("/by-specialty/{specialty}")
    public Response getExamsBySpecialty(@PathParam("specialty") String specialty) {
        List<Exam> exams = examRepository.find("specialty", specialty).list();
        return Response.ok(exams).build();
    }

    @GET
    @Path("/listForCandidates")
    public Response getExamsForCandidates() {
        List<Exam> exams = examRepository.listAll();
        return Response.ok(exams).build();
    }

    @POST
    @Path("/request")
    @Blocking
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response requestExam(@MultipartForm ExamRequestDTO examRequestDto) {
        InputStream receiptStream = null;
        try {
            if (examRequestDto.getCandidateId() == null || examRequestDto.getExamId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("Candidate ID and Exam ID must not be null")
                               .build();
            }

            Candidate candidate = entityManager.find(Candidate.class, examRequestDto.getCandidateId());
            Exam exam = entityManager.find(Exam.class, examRequestDto.getExamId());

            if (candidate == null || exam == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Candidate or Exam not found")
                               .build();
            }

            // Check existing requests in the "exam_requests" table
            TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(er) FROM ExamRequest er WHERE er.candidate.id = :candidateId AND er.exam.id = :examId AND er.status IN ('PENDING', 'ACCEPTED')",
                Long.class
            );
            query.setParameter("candidateId", examRequestDto.getCandidateId());
            query.setParameter("examId", examRequestDto.getExamId());

            Long count = query.getSingleResult();
            if (count > 0) {
                return Response.status(Response.Status.CONFLICT)
                               .entity("Candidate has already submitted a request for this exam")
                               .build();
            }

            ExamRequest examRequest = new ExamRequest();
            examRequest.setCandidate(candidate);
            examRequest.setExam(exam);
            examRequest.setStatus(ExamStatus.valueOf(examRequestDto.getStatus().toUpperCase()));
            examRequest.setRequestDate(examRequestDto.getRequestDate());
            examRequest.setAverageDiplomaDegree(examRequestDto.getAverageDiplomaDegree());
            examRequest.setSubjectDegree(examRequestDto.getSubjectDegree());

            if (examRequestDto.getReceipt() != null) {
                receiptStream = examRequestDto.getReceipt();
                byte[] fileBytes = receiptStream.readAllBytes();
                examRequest.setReceipt(fileBytes);
            }

            examRequestRepository.persist(examRequest);
            return Response.ok("Exam request submitted successfully").build();

        } catch (IOException e) {
            return Response.serverError().entity("Error processing file upload: " + e.getMessage()).build();
        } finally {
            if (receiptStream != null) {
                try {
                    receiptStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GET
    @Path("/myRequests/{candidateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyExamRequests(@PathParam("candidateId") Long candidateId) {
      List<ExamRequest> myRequests = examRequestRepository.find("candidate.id", candidateId).list();

      if (myRequests.isEmpty()) {
          return Response.status(Response.Status.NOT_FOUND)
                  .entity("No exam requests found for this candidate")
                  .build();
      }

      return Response.ok(myRequests).build();
    }

    @PUT
    @Path("/{id}/update-receipt")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Blocking
    public Response updateReceipt(@PathParam("id") Long requestId, @MultipartForm ExamRequestDTO dto) {
        InputStream receiptStream = null;

        try {
            ExamRequest request = examRequestRepository.findById(requestId);
            if (request == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Exam request not found.")
                               .build();
            }

            if (dto.getReceipt() != null) {
                receiptStream = dto.getReceipt();
                byte[] newReceipt = receiptStream.readAllBytes();
                request.setReceipt(newReceipt);
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("No receipt file uploaded.")
                               .build();
            }

            examRequestRepository.persist(request);
            return Response.ok("Receipt updated successfully").build();

        } catch (IOException e) {
            return Response.serverError().entity("Error processing receipt upload: " + e.getMessage()).build();
        } finally {
            if (receiptStream != null) {
                try {
                    receiptStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}