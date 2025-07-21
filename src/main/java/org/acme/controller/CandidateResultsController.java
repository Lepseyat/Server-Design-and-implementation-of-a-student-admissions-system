package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Candidate;
import org.acme.entity.ExamRequest;
import org.acme.entity.ExamStatus;
import org.acme.repository.CandidateRepository;
import org.acme.repository.ExamRequestRepository;

import java.util.List;

@Path("api/exam/result")
public class CandidateResultsController {

    @Inject
    ExamRequestRepository examRequestRepository;

    @Inject
    CandidateRepository candidateRepository;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getCandidateResults(@PathParam("id") Long candidateId) {

        Candidate candidate = candidateRepository.findById(candidateId);

        if (candidate == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Candidate not found with ID: " + candidateId)
                    .build();
        }

        List<ExamRequest> results = examRequestRepository.list(
            "candidate = ?1 AND status = ?2", candidate, ExamStatus.COMPLETED
        );

        return Response.ok(results).build();
    }
}