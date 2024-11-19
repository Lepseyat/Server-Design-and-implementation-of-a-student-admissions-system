package org.acme.resource;

import org.acme.dto.CandidateDTO;
import org.acme.service.CandidateService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
}
