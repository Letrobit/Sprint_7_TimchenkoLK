package ru.practikum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import ru.practikum.dto.CourierCreateRequest;
import ru.practikum.dto.CourierLoginRequest;
import ru.practikum.dto.OrderCreateRequest;
import ru.practikum.dto.OrderDeleteRequest;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class OrderSteps {
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

    public ValidatableResponse createOrder(List<String> color) {
        Random random = new Random();
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setFirstName(RandomStringUtils.randomAlphabetic(10));
        orderCreateRequest.setLastName(RandomStringUtils.randomAlphabetic(10));
        orderCreateRequest.setAddress(RandomStringUtils.randomAlphabetic(10));
        orderCreateRequest.setMetroStation(RandomStringUtils.randomAlphabetic(10));
        orderCreateRequest.setPhone(RandomStringUtils.randomAlphabetic(10));
        orderCreateRequest.setRentTime(random.nextInt(11));
        orderCreateRequest.setDeliveryDate("2020-06-06");
        orderCreateRequest.setComment(RandomStringUtils.randomAlphabetic(10));
        orderCreateRequest.setColor(color);
        return given()
                .contentType(ContentType.JSON)
                .body(orderCreateRequest)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    public ValidatableResponse deleteOrder(Integer track) {
        return given()
                .contentType(ContentType.JSON)
                .pathParams("track", track)
                .when()
                .put("/api/v1/orders/cancel?track={track}")
                .then();

    }
    public ValidatableResponse getOrdersList() {
        return given()
                .get("/api/v1/orders")
                .then();
    }
}
