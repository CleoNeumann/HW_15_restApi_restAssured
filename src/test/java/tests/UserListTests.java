package tests;

import models.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static specs.ReqresSpecs.*;

@Tag("API")
@DisplayName("/users tests")
public class UserListTests extends TestBase {

    @Test
    void getUsersListTest() {
        UsersListResponseModel response = step("Make request", ()->
                given(requestSpec)
                .when()
                .get("/users")
                .then()
                .spec(successResponseSpec200)
                .extract().as(UsersListResponseModel.class)
        );
        step("Check 'page' parameter ", ()->
            assertEquals("1", response.getPage()));
        step("Check 'per_page' parameter", ()->
            assertEquals("6", response.getPerPage()));
        step("Check 'total' parameter", ()->
            assertEquals("12", response.getTotal()));
    }

    @Test
    void checkSelectedUserDataTest() {
        SelectedUserResponseModel response = step("Make request", ()->
                given(requestSpec)
                .when()
                .get("/users/5")
                .then()
                .spec(successResponseSpec200)
                .extract().as(SelectedUserResponseModel.class));
        step("Check user id", ()->
                assertEquals("5", response.getData().getId()));
        step("Check user email", ()->
                assertEquals("charles.morris@reqres.in", response.getData().getEmail()));
        step("Check user name", ()->
                assertEquals("Charles", response.getData().getFirstName()));
        step("Check user surname", ()->
                assertEquals("Morris", response.getData().getLastName()));
    }

    @Test
    public void createNewUserTest() {
        CreateNewUserRequestModel data = new CreateNewUserRequestModel();
        data.setName("Cleo");
        data.setJob("qa");
        CreateNewUserResponseModel response = step("Make request", ()->
                given(requestSpec)
                .body(data)
                .when()
                .post("/users")
                .then()
                .spec(createdResponseSpec201)
                .extract().as(CreateNewUserResponseModel.class));
        step("Check new user name", ()->
                assertEquals(data.getName(), response.getName()));
        step("Check new user job", ()->
                assertEquals(data.getJob(), response.getJob()));
        step("Check new user id not null", ()->
                assertNotNull(response.getId()));
        step("Check new user createdAt equals now()", ()->
                assertThat(response.getCreatedAt().startsWith(String.valueOf(LocalDate.now()))));
    }
}
