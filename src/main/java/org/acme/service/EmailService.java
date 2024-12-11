package org.acme.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EmailService {

    @Inject
    ReactiveMailer mailer;

    public void sendWelcomeEmail(String email, String subject, String body) {
        mailer.send(Mail.withHtml(email, subject, body))
            .subscribe()
            .with(
                success -> System.out.println("Email sent successfully to " + email),
                failure -> System.err.println("Failed to send email: " + failure.getMessage())
            );
    }
}
