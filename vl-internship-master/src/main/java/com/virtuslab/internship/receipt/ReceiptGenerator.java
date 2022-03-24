package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<String> discounts = new ArrayList<>();
        Map<Product, Integer> productInfoHashMap = new HashMap<>();

        for (Product product : basket.getProducts()) {
           Integer occurencies = productInfoHashMap.get(product);
           productInfoHashMap.put(product, (occurencies == null) ? 1 : occurencies + 1);
        }
        for (Map.Entry<Product, Integer> productInfo : productInfoHashMap.entrySet()) {
            receiptEntries.add(new ReceiptEntry(productInfo.getKey(), productInfo.getValue()));
        }
        return new Receipt(receiptEntries, discounts);
    }
}
