package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class AdminGradeExamTest {

    @Test
    public void testGradeExamRequest() {

        String json = """
            {
                "examScore": 90,
                "examGrade": 5,
                "status": "COMPLETED",
                "averageDiplomaDegree": 5,
                "subjectDegree": 5.0
            }
        """;

        float expectedBal = (float)(5 * 3 + 5 + 5); // = 25

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(json)
        .when()
            .put("/api/exam/manage/25/grade")
        .then()
            .statusCode(200)
            .body("examScore", equalTo(90.0f))
            .body("examGrade", equalTo(5.0f))
            .body("status", equalTo("COMPLETED"))
            .body("averageDiplomaDegree", equalTo(5.0f))
            .body("subjectDegree", equalTo(5.0f))
            .body("bal", equalTo(expectedBal));

        System.out.println("Expected BAL: " + expectedBal);
    }

     @Test
     public void testGradeExamRequest_NotFound() {

         String json = """
             {
                 "examScore": 80
             }
         """;

         given()
             .contentType(ContentType.JSON)
             .accept(ContentType.JSON)
             .body(json)
         .when()
             .put("/api/exam/manage/99999/grade")
         .then()
             .statusCode(404)
             .body(containsString("Exam request not found with ID: " + 99999));

         System.out.println("Test for non-existing exam request passed.");
     }
}
