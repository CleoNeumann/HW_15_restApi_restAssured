package tests;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class UserListTests extends TestBase {

    @Test
    void getUsersListTest() {
        given()
                .when()
                .get("/users")
                .then()
                .log().body()
                .statusCode(200)
                .body("total", is(12))
                .body("data[4].first_name", is("Charles"))
                .body("data[4].last_name", is("Morris"));
    }

    @Test
    void checkSelectedUserEmailTest() {
        given()
                .when()
                .get("/users/5")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.email", is("charles.morris@reqres.in"));
    }

    @Test
    public void createNewUserTest() {
        String data = "{\"name\": \"Cleo\", \"job\": \"qa\"}";
        given()
                .body(data)
                .contentType(JSON)
                .log().body()
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Cleo"))
                .body("job", is("qa"))
                .body("createdAt", startsWith(String.valueOf(LocalDate.now())));
    }
}
