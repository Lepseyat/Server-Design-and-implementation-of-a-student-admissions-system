package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.service.RegistrationService;
import org.acme.dto.CandidateDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.security.PermitAll;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class RegistrationController {

    @Inject
    RegistrationService candidateService;

    @POST
    @Path("/register")
    @PermitAll  // Allow access to the registration endpoint without authentication
    public Response createCandidate(CandidateDTO candidateDTO) {
        // Here you can add logic to save the candidate in the database
        candidateService.save(candidateDTO);
        return Response.status(Response.Status.CREATED).build();  // Return a Created status
    }
}
