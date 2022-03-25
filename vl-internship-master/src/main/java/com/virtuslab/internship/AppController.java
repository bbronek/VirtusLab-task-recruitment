package com.virtuslab.internship;

import com.google.gson.Gson;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.lang.reflect.Type;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

@RequestMapping("/api/v1")
@RestController
public class AppController {
    @PostMapping(
            value = "/basket",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> basket(@RequestBody Basket basket) {

        var receiptGenerator = new ReceiptGenerator();

        var receipt = receiptGenerator.generate(basket);
        return ResponseEntity
                .created(URI
                        .create("/receipt"))
                .body(jsonResponse(receipt));
    }

    public String jsonResponse(Receipt receipt) {
        Gson gson = new Gson();
        Type gsonType = new TypeToken<Receipt>(){}.getType();

        return gson.toJson(receipt, gsonType);
    }
}
