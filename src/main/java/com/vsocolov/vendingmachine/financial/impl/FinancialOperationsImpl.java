package com.vsocolov.vendingmachine.financial.impl;

import com.vsocolov.vendingmachine.data.Change;
import com.vsocolov.vendingmachine.enums.Coin;
import com.vsocolov.vendingmachine.financial.FinancialOperations;

import java.util.List;

public class FinancialOperationsImpl implements FinancialOperations {

    @Override
    public List<List<Change>> calculateChanges(final int amount, final List<Coin> availableCoins) {
        // sort coins descending
        availableCoins.sort((obj1, obj2) -> obj1.getAmount() < obj2.getAmount() ? 1 : -1);

        return null;
    }
}
