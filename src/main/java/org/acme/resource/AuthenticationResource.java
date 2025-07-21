package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.service.AuthenticationService;
import jakarta.transaction.Transactional;


@Path("/api/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    @Inject
    AuthenticationService authenticationService;

    @POST
    @Path("/reset-password")
    @Transactional
    public Response sendResetPasswordEmail(EmailRequest request) {

        authenticationService.requestPasswordReset(request.getEmail());

        return Response.ok("If the email exists, a password reset link has been sent.").build();
    }

    public static class EmailRequest {
        public String email;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    @POST
    @Path("/reset-password/confirm")
    @Transactional
    public Response resetPassword(ResetRequest request) {
        boolean result = authenticationService.resetPassword(request.token, request.newPassword);
        if (result) {
            return Response.ok("Password reset successful").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Invalid or expired token").build();
        }
    }

    public static class ResetRequest {
        public String token;
        public String newPassword;
    }

}


