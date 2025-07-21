package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;


@QuarkusTest
public class AdminCreateExamTest {

@Test
public void testCreateExamSuccess() {
    String formattedDate = LocalDateTime.now().plusDays(1).withSecond(0).withNano(0)
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

    String json = """
        {
            "subject": "Biology",
            "specialty": "SIT",
            "examDateTime": "%s"
        }
    """.formatted(formattedDate);

    given()
        .contentType(ContentType.JSON)
        .body(json)
    .when()
        .post("/api/exam/create")
    .then()
        .statusCode(200)
        .body("subject", equalTo("Biology"))
        .body("specialty", equalTo("SIT"))
        .body("examDateTime", containsString(formattedDate));

    System.out.println("Exam created successfully with subject: Biology, specialty: Sit, date: " + formattedDate);
}

    @Test
    public void testCreateExamMissingFields() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"subject\": null,  \"examDateTime\": null, \"specialty\": null}")
        .when()
            .post("/api/exam/create")
        .then()
            .statusCode(400)
            .body(containsString("Subject, Exam Date Time, and Specialty are required."));

        System.out.println("Failed to create exam due to missing fields: subject, examDateTime, specialty.");
    }
}
