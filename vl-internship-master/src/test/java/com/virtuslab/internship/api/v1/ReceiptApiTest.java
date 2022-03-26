package com.virtuslab.internship.api.v1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReceiptApiTest {
    @LocalServerPort
    int randomServerPort;

    @Test
    void shouldReturn201StatusCodeAfterSendingProperBasketJson() {
        String basketJson = generateJSONBasket();

        given().
            port(randomServerPort).
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(basketJson).
            when().
                post("/api/v1/basket").
            then().
                statusCode(201);
    }

    @Test
    void shouldReturn400StatusCodeAfterSendingWrongData() {
        String wrongData = "{wrongData: wrongData}";

        given().
                port(randomServerPort).
                    header("Content-Type", "application/json").
                    contentType(ContentType.JSON).
                    accept(ContentType.JSON).
                    body(wrongData).
                when().
                    post("/api/v1/basket").
                then().
                    statusCode(400);
    }

    @Test
    void shouldReturn405StatusCodeIfClientUsedGetRequest() {
        String basketJson = generateJSONBasket();

        given().
                port(randomServerPort).
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(basketJson).
                when().
                get("/api/v1/basket").
                then().
                statusCode(405);
    }

    String generateJSONBasket() {
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(apple);

        Gson gson = new Gson();
        Type gsonType = new TypeToken<Basket>(){}.getType();

        return gson.toJson(cart, gsonType);
    }
}
