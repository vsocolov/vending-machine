package com.vsocolov.vendingmachine.productstorage.data;

import org.apache.commons.lang3.RandomStringUtils;

public class Product {
    private static final int DEFAULT_PRODUCT_NAME_SIZE = 5;
    private static final int DEFAULT_PRICE = 100;
    private static final int DEFAULT_QUANTITY = 0;

    private final int id;
    private final String name;
    private final int price;
    private final int quantity;

    public Product(int id) {
        this.id = id;
        this.name = RandomStringUtils.random(DEFAULT_PRODUCT_NAME_SIZE, true, false);
        this.price = DEFAULT_PRICE;
        this.quantity = DEFAULT_QUANTITY;
    }

    public Product(int id, final String name, final int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
