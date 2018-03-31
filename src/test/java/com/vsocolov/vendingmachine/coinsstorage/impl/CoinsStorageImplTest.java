package com.vsocolov.vendingmachine.coinsstorage.impl;

import com.vsocolov.vendingmachine.coinsstorage.CoinsStorage;
import com.vsocolov.vendingmachine.enums.ExceptionType;
import com.vsocolov.vendingmachine.exceptions.VendingMachineException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static com.vsocolov.vendingmachine.enums.Coin.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CoinsStorageImplTest {

    private CoinsStorage coinsStorage;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        coinsStorage = new CoinsStorageImpl(Arrays.asList(ONE_PENNY, FIVE_PENCE, TEN_PENCE));
    }

    @Test
    public void getCoinAmount_should_return_default_coins_amount() {
        int coinAmount = coinsStorage.getCoinAmount(FIVE_PENCE);
        assertThat(coinAmount, equalTo(CoinsStorageImpl.DEFAULT_AMOUNT));
    }

    @Test
    public void getCoinAmount_should_throw_exception_if_you_extract_unsupported_coin() {
        expectedException.expect(VendingMachineException.class);
        expectedException.expectMessage(equalTo(ExceptionType.UNSUPPORTED_COINS.getMessage()));
        coinsStorage.getCoinAmount(ONE_POUND);
    }

    @Test
    public void setCoinAmount_should_update_coin_amount_in_storage() {
        // set new coin amount
        coinsStorage.setCoinAmount(FIVE_PENCE, 100);

        assertThat(coinsStorage.getCoinAmount(FIVE_PENCE), equalTo(100));
    }

    @Test
    public void setCoinAmount_should_throw_exception_if_you_extract_unsupported_coin() {
        expectedException.expect(VendingMachineException.class);
        expectedException.expectMessage(equalTo(ExceptionType.UNSUPPORTED_COINS.getMessage()));
        coinsStorage.setCoinAmount(TWO_POUNDS, 100);
    }
}