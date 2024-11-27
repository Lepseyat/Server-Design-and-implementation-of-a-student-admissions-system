package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Candidate;
import org.acme.service.AuthenticationService;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginController {

    @Inject
    AuthenticationService authService;

    @POST
    @Path("/login")
    public Response login(Candidate loginRequest) {
        boolean authenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (authenticated) {
            return Response.ok("Login successful!").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build();
    }
}
