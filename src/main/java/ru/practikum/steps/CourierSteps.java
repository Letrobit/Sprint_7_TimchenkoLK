package ru.practikum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.practikum.dto.CourierCreateRequest;
import ru.practikum.dto.CourierLoginRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CourierSteps {
    public ValidatableResponse createCourier(String login, String password, String name) {
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest();
        courierCreateRequest.setLogin(login);
        courierCreateRequest.setPassword(password);
        courierCreateRequest.setName(name);
        return given()
                .contentType(ContentType.JSON)
                .body(courierCreateRequest)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse loginCourier(String login, String password) {
        CourierLoginRequest courierLoginRequest = new CourierLoginRequest();
        courierLoginRequest.setLogin(login);
        courierLoginRequest.setPassword(password);
        return given()
                .contentType(ContentType.JSON)
                .body(courierLoginRequest)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse deleteCourier(int id) {
        return given()
                .contentType(ContentType.JSON)
                .pathParams("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }

    public ValidatableResponse createCourierPasswordFieldMissing(String login, String name) {
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest();
        courierCreateRequest.setLogin(login);
        courierCreateRequest.setName(name);
        return given()
                .contentType(ContentType.JSON)
                .body(courierCreateRequest)
                .when()
                .post("/api/v1/courier")
                .then();
    }
}
