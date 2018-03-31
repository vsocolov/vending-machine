package com.vsocolov.vendingmachine.financial;

import com.vsocolov.vendingmachine.data.Change;
import com.vsocolov.vendingmachine.enums.Coin;

import java.util.List;

public interface FinancialOperations {

    List<List<Change>> calculateChanges(int amount, List<Coin> availableCoins);
}
