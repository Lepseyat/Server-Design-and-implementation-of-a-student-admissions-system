package org.acme.resource;

import org.acme.dto.CandidateDTO;
import org.acme.service.CandidateService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/candidates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CandidateResource {

    @Inject
    CandidateService candidateService;

    @GET
    public List<CandidateDTO> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @POST
    public Response createCandidate(CandidateDTO candidateDTO) {
        candidateService.save(candidateDTO);
        return Response.status(Response.Status.CREATED).build();
    }
}
