package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@QuarkusTest
public class CandidateResultsTest {

    @Test
    public void testGetCandidateResults_Success() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/api/exam/result/1")
        .then()
            .statusCode(200)
            .body("$", hasSize(greaterThanOrEqualTo(1)))
            .body("[0].status", equalTo("COMPLETED"));

        System.out.println("Candidate results retrieved successfully for candidate ID 1.");
    }

    @Test
    public void testGetCandidateResults_CandidateNotFound() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/api/exam/result/99999")
        .then()
            .statusCode(404)
            .body(containsString("Candidate not found with ID: 99999"));

        System.out.println("Candidate not found with ID 99999, as expected.");
    }
}