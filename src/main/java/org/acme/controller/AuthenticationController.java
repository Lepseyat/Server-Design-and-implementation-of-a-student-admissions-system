package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.service.AuthenticationService;
import org.acme.service.RegistrationService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.smallrye.jwt.build.Jwt;

import org.acme.dto.CandidateDTO;
import org.acme.resource.LoginRequest;
import org.acme.resource.TokenResponse;
import jakarta.enterprise.context.ApplicationScoped;
import io.smallrye.jwt.auth.principal.JWTParser;
import java.util.List;


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
    String token = authService.authenticateAndGenerateToken(
        loginRequest.getUsername(),
        loginRequest.getPassword()
    );

    if (token != null) {
        // Parse the generated token to extract claims
        JsonWebToken parsedToken = authService.parseToken(token);
        boolean isAdmin = false;

        // Get the "role" claim
        String role = parsedToken.getClaim("role");

        // Debugging output: print the raw role and its value
        System.out.println("Role claim: " + role);

        // Check if the role is "ADMIN"
        if ("ADMIN".equals(role)) {
            isAdmin = true;
        }

        System.out.println("Is Admin: " + isAdmin);  // Debugging output

        // Return the token along with admin information
        return Response.ok(new TokenResponse(token, isAdmin)).build();
    }

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

