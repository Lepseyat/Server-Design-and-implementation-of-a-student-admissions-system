package org.acme.resource;

import org.acme.entity.Candidate;
import org.acme.entity.Exam;
import org.acme.entity.ExamRequest;
import org.acme.entity.ExamStatus;
import org.acme.repository.ExamRequestRepository;
import org.acme.dto.ExamRequestDTO;  // Import DTO
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import jakarta.transaction.Transactional;
import java.io.InputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List; 

@Path("/exam")
public class ExamRequestResource {

    @Inject
    ExamRequestRepository examRequestRepository;

    @Inject
    EntityManager entityManager; 

    @POST
@Path("/request")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Transactional
public Response requestExam(@FormParam("candidate_id") Long candidateId,
                            @FormParam("exam_id") Long examId,
                            @FormParam("exam_score") Double examScore,
                            @FormParam("status") String statusStr,
                            @FormParam("receipt") InputStream receiptInputStream) {

    // Convert status string to ExamStatus enum
    ExamStatus status;
    try {
        status = ExamStatus.valueOf(statusStr.toUpperCase());
    } catch (IllegalArgumentException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity("Invalid status provided")
                       .build();
    }

    // Read receipt bytes if available
    byte[] receiptBytes = null;
    if (receiptInputStream != null) {
        try {
            receiptBytes = receiptInputStream.readAllBytes();
            receiptInputStream.close();  
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error reading receipt")
                           .build();
        }
    }

    // ✅ Fetch Candidate and Exam using EntityManager
    Candidate candidate = entityManager.find(Candidate.class, candidateId);
    Exam exam = entityManager.find(Exam.class, examId);

    if (candidate == null || exam == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity("Candidate or Exam not found")
                       .build();
    }

    // ExamRequest object and set Candidate and Exam objects
    ExamRequest examRequest = new ExamRequest();
    examRequest.setCandidate(candidate);  // Candidate object, not ID
    examRequest.setExam(exam);            // Exam object, not ID
    examRequest.setExamScore(examScore);
    examRequest.setRequestDate(LocalDateTime.now());
    examRequest.setStatus(status);
    examRequest.setReceipt(receiptBytes);

    // Persist the request
    examRequestRepository.persist(examRequest);

    return Response.ok().entity("Exam request submitted with receipt.").build();
}

    @GET
    @Path("/pendingRequests")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingExamRequests() {
        List<ExamRequestDTO> requests = examRequestRepository.findPendingRequests();
        return Response.ok(requests).build();
    }
}
