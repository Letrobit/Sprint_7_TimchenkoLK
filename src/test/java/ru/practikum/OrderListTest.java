package ru.practikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.Test;
import ru.practikum.steps.OrderSteps;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;


public class OrderListTest {
    private OrderSteps orderSteps = new OrderSteps();


    @Before
    public void setup() {
        //RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void getOrderListShouldReturnOrders() {
        orderSteps.getOrdersList().statusCode(200).body("orders", is(notNullValue()));
    }
}
