package com.vsocolov.vendingmachine.financial.impl;

import com.vsocolov.vendingmachine.data.Change;
import com.vsocolov.vendingmachine.enums.Coin;
import com.vsocolov.vendingmachine.financial.FinancialOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FinancialOperationsImpl implements FinancialOperations {

    /**
     * This list compute list of possible changes
     *
     * @param amount         amount to compute change
     * @param availableCoins list of available coins
     * @return a list of changes
     */
    @Override
    public List<List<Change>> calculateChanges(final int amount, final List<Coin> availableCoins) {
        // sort coins descending
        availableCoins.sort((obj1, obj2) -> obj1.getAmount() < obj2.getAmount() ? 1 : -1);

        final List<List<Change>> changes = new ArrayList<>();
        for (int i = 0; i < availableCoins.size(); i++) {
            final Coin coin = availableCoins.get(i);
            if (coin.getAmount() <= amount) {
                changes.add(doCalculate(i, amount, availableCoins));
            }
        }

        return changes;
    }

    private List<Change> doCalculate(final int index, final int amount, final List<Coin> availableCoins) {
        if (amount <= 0 || index >= availableCoins.size())
            return Collections.emptyList();

        final Coin coin = availableCoins.get(index);
        int remainingAmount = amount;
        int coinCounter = 0;

        while (remainingAmount - coin.getAmount() >= 0) {
            remainingAmount -= coin.getAmount();
            coinCounter++;
        }

        final List<Change> changeList = new ArrayList<>();
        changeList.add(new Change(coin, coinCounter)); //add current coin with quantity
        changeList.addAll(doCalculate(index + 1, remainingAmount, availableCoins)); //add remaining coins

        return changeList;
    }
}
