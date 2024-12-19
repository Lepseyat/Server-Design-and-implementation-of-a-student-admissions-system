package org.acme.controller;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import org.acme.service.ExamService; 
import org.acme.dto.ExamRequestDTO;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/exam")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExamController {

    @Inject
    private SecurityContext securityContext; // Inject SecurityContext

    @Inject
    ExamService examService; // A service class to handle business logic and database operations

    @POST
    @Path("/schedule")
    public Response scheduleExam(ExamRequestDTO request) {
        // Debugging the authentication process
        if (securityContext.getUserPrincipal() != null) {
            String candidateId = securityContext.getUserPrincipal().getName();
            System.out.println("Authenticated user: " + candidateId);  // Should print the user's name
        } else {
            System.out.println("No authenticated user found.");  // Will print if the token is invalid or missing
        }

        try {
            // Scheduling the exam
            examService.scheduleExam(request); // Pass request data to the service
            return Response.status(Response.Status.CREATED).entity("Exam scheduled successfully!").build();
        } catch (Exception e) {
            // Log the full exception stack trace for better debugging
            e.printStackTrace();  // Or use a logging framework like SLF4J or Log4j for better logging
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error scheduling exam: " + e.getClass().getName() + " - " + e.getMessage())
                           .build();
        }
    }
}
