package com.vsocolov.vendingmachine.helpers;

import com.vsocolov.vendingmachine.data.Change;
import com.vsocolov.vendingmachine.exceptions.VendingMachineException;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

import static com.vsocolov.vendingmachine.enums.ExceptionType.*;

public final class AssertionHelper {

    public static void assertValueIsPositive(final int quantity) {
        if(quantity < 0)
            throw new VendingMachineException(PROPERTY_SHOULD_BE_POSITIVE);
    }

    public static void assertProductAvailable(final int productQuantity) {
        if (productQuantity == 0)
            throw new VendingMachineException(PRODUCT_NOT_AVAILABLE);
    }

    public static void assertEnoughMoney(final int productPrice, final int coinsAmount) {
        if (productPrice > coinsAmount)
            throw new VendingMachineException(NOT_ENOUGH_MONEY);
    }

    public static void assertEnoughChange(final List<Change> change, final int paidAmount, final int productPrice) {
        if (paidAmount > productPrice && CollectionUtils.isEmpty(change))
            throw new VendingMachineException(NOT_ENOUGH_CHANGE);
    }

    public static void assertProductId(final int productId, final int capacity) {
        if (productId < 0 || productId >= capacity)
            throw new VendingMachineException(INVALID_PRODUCT_SLOT);
    }
}
