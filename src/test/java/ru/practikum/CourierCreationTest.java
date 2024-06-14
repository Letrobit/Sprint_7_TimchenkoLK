package ru.practikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practikum.steps.CourierSteps;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.notNullValue;

public class CourierCreationTest {
    private CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    private String name;

    @Before
    public void setup() {
        //RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        baseURI = "http://qa-scooter.praktikum-services.ru/";
    }


    @After
    public void tearDown() {
        if ((password != null) & (login != null)) {
            Integer id = courierSteps.loginCourier(login, password)
                    .extract().body().path("id");
            if (id != null) {
                courierSteps.deleteCourier(id);
            }
        }
    }

    @Test
    public void CreateCourierCanBeCreated() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        name = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, name)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    public void CreateCourierTwiceShouldReturn409() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        name = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, name);
        courierSteps
                .createCourier(login, password, name)
                .statusCode(409)
                .body("message", is(notNullValue()));
    }

    @Test
    public void CreateCourierAllFieldsMustBePassed() {
        login = null;
        password = null;
        name = null;
        courierSteps
                .createCourier(login, password, name)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void CreateCourierShouldReturn201() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        name = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, name)
                .statusCode(201);
    }
    @Test
    public void CreateCourierShouldReturn400WhenNotEnoughData() {
        login = null;
        password = null;
        name = null;
        courierSteps
                .createCourier(login, password, name)
                .statusCode(400);
    }

    @Test
    public void CreateCourierShouldReturnOkTrue() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        name = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, name)
                .body("ok", is(true));
    }


    @Test
    public void CreateCourierOneFieldMissing() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = null;
        name = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, name)
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }

    @Test
    public void CreateCourierWhenLoginExistsReturnsError() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        name = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, name);
        courierSteps
                .createCourier(login, password, name)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }
}
