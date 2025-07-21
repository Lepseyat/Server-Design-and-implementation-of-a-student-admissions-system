package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.service.AuthenticationService;
import org.acme.service.RegistrationService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.acme.dto.CandidateDTO;
import org.acme.resource.LoginRequest;
import org.acme.resource.TokenResponse;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AuthenticationController {

    @Inject
    AuthenticationService authService;

    @Inject
    JsonWebToken jwt;

    @Inject
    RegistrationService candidateService;

    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        String token = authService.authenticateAndGenerateToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );

        if (token != null) {
            JsonWebToken parsedToken = authService.parseToken(token);
            boolean isAdmin = false;

            String role = parsedToken.getClaim("role");
        
            System.out.println("Role claim: " + role);

            if ("ADMIN".equals(role)) {
                isAdmin = true;
            }

            System.out.println("Is Admin: " + isAdmin);

            return Response.ok(new TokenResponse(token, isAdmin)).build();
        }

    return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
}   

    //@GET
    //@Path("/profile")
    //public Response getProfile() {
    //    String userId = jwt.getSubject();
    //
    //    CandidateDTO candidate = candidateService.getCandidateById(Long.parseLong(userId));
    //    if (candidate != null) {
    //        return Response.ok(candidate).build();
    //    }
    //
    //    return Response.status(Response.Status.NOT_FOUND).entity("User profile not found").build();
    //}

    @POST
    @Path("/registration")
    @PermitAll
    public Response createCandidate(CandidateDTO candidateDTO) {
        candidateService.save(candidateDTO);
        return Response.status(Response.Status.CREATED).build();
    }

}

