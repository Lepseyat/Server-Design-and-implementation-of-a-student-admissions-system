package org.acme.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

public class EmailService {

    @Inject
    ReactiveMailer mailer;

    public void sendEmail(String recipient, String password) {
        String body = String.format(
            "<html><body><h3>Welcome!</h3><p>Your password is: %s</p></body></html>", password);
        
        mailer.send(Mail.withHtml(recipient, "Welcome to our service", body));
    }
}
