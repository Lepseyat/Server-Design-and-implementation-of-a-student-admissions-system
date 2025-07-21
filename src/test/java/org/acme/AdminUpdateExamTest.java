package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class AdminUpdateExamTest {

    @Test
    public void testUpdateExam() {
        String updatedDate = LocalDateTime.now().plusDays(5).withSecond(0).withNano(0)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        String json = """
            {
                "subject": "Updated Biology",
                "examDateTime": "%s"
            }
        """.formatted(updatedDate);

        given()
            .contentType(ContentType.JSON)
            .body(json)
            .log().all()
        .when()
            .put("/api/exam/10/update")
        .then()
            .log().all()
            .statusCode(200)
            .body("subject", equalTo("Updated Biology"))
            .body("examDateTime", containsString(updatedDate));

        System.out.println("Exam updated successfully with new subject: Updated Biology and date: " + updatedDate);
    }

    @Test
    public void testUpdateExamNotFound() {
        String json = """
            {
                "subject": "Nonexistent Exam",
                "examDateTime": "2023-12-31T10:00:00"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .put("/api/exam/99999/update")
        .then()
            .statusCode(404)
            .body(containsString("Exam not found"));

        System.out.println("Attempted to update a non-existent exam, received 404 Not Found response.");
    }
}
