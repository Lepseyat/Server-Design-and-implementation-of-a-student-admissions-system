package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.service.AuthenticationService;
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
}
