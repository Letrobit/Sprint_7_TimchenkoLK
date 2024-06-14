package ru.practikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practikum.steps.OrderSteps;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {
    private List<String> color;
    private OrderSteps orderSteps = new OrderSteps();
    private Integer track;


    public OrderTest(List<String> color) {
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] getOrderData(){
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GRAY")},
                {List.of("")}
        };
    }
    @Before
    public void setup() {
        //RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void tearDown() {
        if (track != null) {
            orderSteps.deleteOrder(track);
        }
    }

    @Test
    public void createOrderWithParams(){
        track = orderSteps
                .createOrder(color)
                .statusCode(201)
                .body("track", is(notNullValue()))
                .extract()
                .body()
                .path("track");
    }
}
