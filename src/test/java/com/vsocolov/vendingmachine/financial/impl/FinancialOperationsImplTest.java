package com.vsocolov.vendingmachine.financial.impl;

import com.vsocolov.vendingmachine.financial.FinancialOperations;
import org.junit.Test;

import java.util.Arrays;

import static com.vsocolov.vendingmachine.enums.Coin.*;

public class FinancialOperationsImplTest {

    private FinancialOperations financialOperations = new FinancialOperationsImpl();

    @Test
    public void calculateChanges() {
        financialOperations.calculateChanges(150, Arrays.asList(ONE_PENNY, FIVE_PENCE, TEN_PENCE, TWENTY_PENCE));
    }
}