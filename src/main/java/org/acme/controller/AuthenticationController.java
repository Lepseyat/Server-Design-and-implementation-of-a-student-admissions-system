package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.service.AuthenticationService;
import org.acme.service.RegistrationService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.acme.dto.CandidateDTO;
import org.acme.resource.LoginRequest; // Import the LoginRequest class
import org.acme.resource.TokenResponse;
import jakarta.enterprise.context.ApplicationScoped;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AuthenticationController {

    @Inject
    AuthenticationService authService;

    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        // Authenticate and generate JWT token
        String token = authService.authenticateAndGenerateToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );
        if (token != null) {
            // Return the generated token as the response
            return Response.ok(new TokenResponse(token)).build();
        }
        // If authentication fails, return Unauthorized
        return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
    }

    @Inject
JsonWebToken jwt;

@Inject
RegistrationService candidateService;


    @GET
@Path("/profile")
public Response getProfile() {
    // Extract the user ID from the JWT token
    String userId = jwt.getSubject();

    // Fetch user details using the CandidateService
    CandidateDTO candidate = candidateService.getCandidateById(Long.parseLong(userId));
    if (candidate != null) {
        return Response.ok(candidate).build();
    }
    return Response.status(Response.Status.NOT_FOUND).entity("User profile not found").build();
}

}
