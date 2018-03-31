package com.vsocolov.vendingmachine.enums;

public enum ExceptionType {
    INVALID_PRODUCT_SLOT("Selected product slot is invalid."),
    UNSUPPORTED_COINS("Vending machine do not support such type of coins."),
    PRODUCT_NOT_AVAILABLE("Product is not available. Quantity is zero."),
    NOT_ENOUGH_MONEY("Not enough money to buy this product.");

    private final String message;

    ExceptionType(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
