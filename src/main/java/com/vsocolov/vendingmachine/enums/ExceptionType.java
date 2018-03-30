package com.vsocolov.vendingmachine.enums;

public enum ExceptionType {
    INVALID_PRODUCT_SLOT("Selected product slot is invalid.");

    private final String message;

    ExceptionType(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
