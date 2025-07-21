package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class AuthenticationControllerTest {

    @Test
    public void testLoginSuccess() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"username\":\"dtm12@abv.bg\",\"password\":\"TPtp50$%\"}")
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(200)
            .body("token", notNullValue())
            .body("admin", equalTo(true));

        System.out.println("Login successful, token received.");      
    }

    @Test
    public void testLoginFailure() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"username\":\"dtm12@abv.bg\",\"password\":\"12345\"}")
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(401)
            .body(containsString("Invalid credentials"));

        System.out.println("Login failed, invalid credentials.");
    }
}
