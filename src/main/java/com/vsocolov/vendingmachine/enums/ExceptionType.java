package com.vsocolov.vendingmachine.enums;

public enum ExceptionType {
    INVALID_PRODUCT_SLOT("Selected product slot is invalid."),
    UNSUPPORTED_COINS("Vending machine do not support such type of coins.");

    private final String message;

    ExceptionType(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
