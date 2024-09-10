package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;

public class RegistrationTests extends TestBase {

    @Test
    void successfulRegistrationTest() {
        String data = "{\"email\": \"eve.holt@reqres.in\"," +
                " \"password\": \"pistol\"}";
        String token = given()
                .body(data)
                .contentType(JSON)
                .log().body()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", notNullValue())
                .extract().path("token");
        assertThat(token)
                .hasSizeGreaterThan(10)
                .isAlphanumeric();
    }

    @Test
    void registrationWithNoPasswordTest() {
        String data = "{\"email\": \"eve.holt@reqres.in\"}";
        given()
                .body(data)
                .contentType(JSON)
                .log().body()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void registrationWithNoEmailTest() {
        String data = "{\"password\": \"pistol\"}";
        given()
                .body(data)
                .contentType(JSON)
                .log().body()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}
