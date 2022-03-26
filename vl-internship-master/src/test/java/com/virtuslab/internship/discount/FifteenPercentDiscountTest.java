package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FifteenPercentDiscountTest {

    @Test
    void shouldApply15PercentDiscountWhenNumberOfGrainsIsExactly3() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<String> discounts = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(receiptEntries, discounts);
        var discount = new FifteenPercentDiscount();
        var expectedTotalPrice = bread.price().add(cereals.price().multiply(BigDecimal.valueOf(2))).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApply15PercentDiscountWhenNumberOfGrainsIsGreaterThan3() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<String> discounts = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(receiptEntries, discounts);
        var discount = new FifteenPercentDiscount();
        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(2))
                .add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApply10PercentDiscountWhenNumberOfGrainsIsGreaterThan3AndPriceIsAbove50After15PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<String> discounts = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 9));

        var receipt = new Receipt(receiptEntries, discounts);
        var discount1 = new FifteenPercentDiscount();
        var discount2 = new TenPercentDiscount();
        var expectedTotalPrice = cheese.price().add(steak.price())
                .add(cereals.price()
                        .multiply(BigDecimal.valueOf(9)))
                .multiply(BigDecimal.valueOf(0.765));

        // When
        var receiptAfterDiscount1 = discount1.apply(receipt);
        var receiptAfterDiscount2 = discount2.apply(receiptAfterDiscount1);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount2.totalPrice());
        assertEquals(2, receiptAfterDiscount2.discounts().size());

    }

    @Test
    void shouldApplyOnly15PercentDiscountWhenNumberOfGrainsIsGreaterThan3AndPriceIsBelow50() {
        // Given
        var productDb = new ProductDb();
        var onion = productDb.getProduct("Onion");
        var bread = productDb.getProduct("Bread");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<String> discounts = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(onion, 2));
        receiptEntries.add(new ReceiptEntry(bread, 4));

        var receipt = new Receipt(receiptEntries, discounts);
        var discount1 = new FifteenPercentDiscount();
        var discount2 = new TenPercentDiscount();
        var expectedTotalPrice = onion.price()
                .multiply(BigDecimal.valueOf(2))
                .add(bread.price()
                        .multiply(BigDecimal.valueOf(4)))
                .multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount1 = discount1.apply(receipt);
        var receiptAfterDiscount2 = discount2.apply(receiptAfterDiscount1);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount2.totalPrice());
        assertEquals(1, receiptAfterDiscount2.discounts().size());
    }

    @Test
    void shouldNotApplyAnyPercentDiscountsWhenNumberOfGrainsIsBelow3AndPriceIsAbove50() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<String> discounts = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));

        var receipt = new Receipt(receiptEntries, discounts);
        var discount1 = new FifteenPercentDiscount();
        var discount2 = new TenPercentDiscount();
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(2));

        // When
        var receiptAfterDiscount1 = discount1.apply(receipt);
        var receiptAfterDiscount2 = discount2.apply(receiptAfterDiscount1);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount2.totalPrice());
        assertEquals(0, receiptAfterDiscount2.discounts().size());
    }

    @Test
    void shouldApplyOnly10PercentDiscountWhenNumberOfGrainsIsBelow3AndPriceIsAbove50() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<String> discounts = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries, discounts);
        var discount1 = new FifteenPercentDiscount();
        var discount2 = new TenPercentDiscount();
        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(2))
                .add(steak.price().multiply(BigDecimal.valueOf(1)))
                .multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount1 = discount1.apply(receipt);
        var receiptAfterDiscount2 = discount2.apply(receiptAfterDiscount1);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount2.totalPrice());
        assertEquals(1, receiptAfterDiscount2.discounts().size());
    }
}
