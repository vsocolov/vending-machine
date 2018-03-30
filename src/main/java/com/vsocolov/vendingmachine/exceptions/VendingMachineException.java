package com.vsocolov.vendingmachine.exceptions;

import com.vsocolov.vendingmachine.enums.ExceptionType;

public class VendingMachineException extends RuntimeException {
    public VendingMachineException(final ExceptionType exceptionType) {
        super(exceptionType.getMessage());
    }
}
