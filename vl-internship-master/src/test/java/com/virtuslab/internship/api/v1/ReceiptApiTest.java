package com.virtuslab.internship.api.v1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReceiptApiTest {
    @LocalServerPort
    int randomServerPort;

    @Test
    void shouldReturn201StatusCodeAfterSendingProperBasketJson() {
        Basket cart = generateBasket();
        Gson gson = new Gson();
        Type gsonType = new TypeToken<Basket>(){}.getType();

        given().
            port(randomServerPort).
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(gson.toJson(cart, gsonType)).
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
        Basket cart = generateBasket();
        Gson gson = new Gson();
        Type gsonType = new TypeToken<Basket>(){}.getType();

        given().
            port(randomServerPort).
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(gson.toJson(cart, gsonType)).
            when().
                get("/api/v1/basket").
            then().
                statusCode(405);
    }

    @Test
    void shouldReturnProperJsonData() {
        Basket cart = generateBasket();
        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);

        Gson gson = new Gson();
        Type gsonCartType = new TypeToken<Basket>(){}.getType();
        Type gsonReceiptType = new TypeToken<Receipt>(){}.getType();

        String responseJSON = given().
                        port(randomServerPort).
                            header("Content-Type", "application/json").
                            contentType(ContentType.JSON).
                            accept(ContentType.JSON).
                            body(gson.toJson(cart, gsonCartType)).
                        when().
                            post("/api/v1/basket").
                        then().
                            statusCode(201).
                        extract().
                        asString();

        assertEquals(responseJSON, gson.toJson(receipt, gsonReceiptType));
    }

    Basket generateBasket() {
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(apple);

        return cart;
    }
}
