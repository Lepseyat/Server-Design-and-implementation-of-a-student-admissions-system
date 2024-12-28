package org.acme.controller;

import org.acme.dto.CandidateDTO;
import org.acme.service.RegistrationService;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/candidates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CandidateResourceController {

    @Inject
    RegistrationService candidateService;

    @GET
    public List<CandidateDTO> getAllCandidates() {
        return candidateService.getAllCandidates();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCandidateById(@PathParam("id") Long id) {
        CandidateDTO candidate = candidateService.getCandidateById(id);
        if (candidate != null) {
            return Response.ok(candidate).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
