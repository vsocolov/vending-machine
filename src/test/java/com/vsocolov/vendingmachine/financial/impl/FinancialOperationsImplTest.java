package com.vsocolov.vendingmachine.financial.impl;

import com.vsocolov.vendingmachine.data.Change;
import com.vsocolov.vendingmachine.enums.Coin;
import com.vsocolov.vendingmachine.financial.FinancialOperations;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.vsocolov.vendingmachine.enums.Coin.*;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

public class FinancialOperationsImplTest {

    private FinancialOperations financialOperations = new FinancialOperationsImpl();

    @Test
    public void calculateChanges_should_return_a_list_of_possible_coin_computations() {
        final String quantityProp = "quantity";
        final String coinProp = "coin";
        final List<Coin> coins = Arrays.asList(ONE_PENNY, FIVE_PENCE, TEN_PENCE, TWENTY_PENCE);

        final List<List<Change>> changes = financialOperations.calculateChanges(15, coins);

        assertThat(changes, hasSize(3));
        assertThat(changes.get(0), hasItems(hasProperty(coinProp, equalTo(Coin.TEN_PENCE)), hasProperty(quantityProp, equalTo(1))));
        assertThat(changes.get(0), hasItems(hasProperty(coinProp, equalTo(Coin.FIVE_PENCE)), hasProperty(quantityProp, equalTo(1))));
        assertThat(changes.get(1), hasItems(hasProperty(coinProp, equalTo(Coin.FIVE_PENCE)), hasProperty(quantityProp, equalTo(3))));
        assertThat(changes.get(2), hasItems(hasProperty(coinProp, equalTo(Coin.ONE_PENNY)), hasProperty(quantityProp, equalTo(15))));
    }

    @Test
    public void calculateChanges_should_return_empty_list_if_its_not_possible_to_compute_changes() {
        final List<Coin> coins = Arrays.asList(TWENTY_PENCE, ONE_POUND);

        final List<List<Change>> changes = financialOperations.calculateChanges(15, coins);
        assertThat(changes, is(empty()));
    }
}