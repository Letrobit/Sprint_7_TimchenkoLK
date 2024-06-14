package ru.practikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practikum.steps.CourierSteps;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    private CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
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
    public void loginCourierShouldReturn200() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, "Saske");

        courierSteps
                .loginCourier(login, password)
                .statusCode(200);

    }
    @Test
    public void loginCourierAllFieldsMustBePassedShouldReturn400() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, "Saske");

        courierSteps
                .loginCourier(null, password)
                .statusCode(400);
    }
    @Test
    public void loginCourierWrongLoginShouldReturnError() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, "Saske");
        courierSteps
                .loginCourier("aaaaaaaaaaa", password) //Такой пароль невозможен, так как в нем 11 символов,
                .body("message", is(notNullValue()));    //что больше максимального количества, которое генерирует RandomStringUtils
    }

    @Test
    public void loginCourierWhenFieldIsNotPassedShouldReturnError() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, "Saske");

        courierSteps
                .loginCourier(null, password)
                .body("message", is(notNullValue()));
    }

    @Test
    public void loginCourierWhenWrongCredentialsShouldReturnError() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, "Saske");

        courierSteps
                .loginCourier("ааааааааааа", "ааааааааааа")
                .body("message", is(notNullValue()));
    }
    @Test
    public void loginCourierShouldReturnId() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, "Saske");

        Integer id = courierSteps.loginCourier(login, password)
                .extract().body().path("id");
        courierSteps
                .loginCourier(login, password)
                .body("id", is(id));
    }

}
