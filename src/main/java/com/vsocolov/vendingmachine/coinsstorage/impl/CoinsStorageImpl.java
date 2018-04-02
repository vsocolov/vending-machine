package com.vsocolov.vendingmachine.coinsstorage.impl;

import com.vsocolov.vendingmachine.coinsstorage.CoinsStorage;
import com.vsocolov.vendingmachine.enums.Coin;
import com.vsocolov.vendingmachine.enums.ExceptionType;
import com.vsocolov.vendingmachine.exceptions.VendingMachineException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CoinsStorageImpl implements CoinsStorage {

    protected static final Integer DEFAULT_AMOUNT = 0;

    private final Map<Coin, Integer> coinsStorage;

    public CoinsStorageImpl(final List<Coin> coins) {
        this.coinsStorage = fillStorage(coins);
    }

    @Override
    public int getCoinAmount(final Coin coin) {
        assertCoin(coin);
        synchronized (coinsStorage) {
            return coinsStorage.get(coin);
        }
    }

    @Override
    public void setCoinAmount(final Coin coin, final int amount) {
        assertCoin(coin);
        synchronized (coinsStorage) {
            coinsStorage.put(coin, amount);
        }
    }

    @Override
    public void decreaseCoinAmount(final Coin coin, final int decreaseAmount) {
        assertCoin(coin);
        synchronized (coinsStorage) {
            final Integer coinAmount = coinsStorage.get(coin);
            coinsStorage.put(coin, coinAmount - decreaseAmount);
        }
    }

    @Override
    public void increaseCoinAmount(final Coin coin, final int increasedAmount) {
        assertCoin(coin);
        synchronized (coinsStorage) {
            final Integer coinAmount = coinsStorage.get(coin);
            coinsStorage.put(coin, coinAmount + increasedAmount);
        }
    }

    /**
     * This method create a map of supported coins with predefined amount "0".
     *
     * @param coins list of supported coins
     * @return result coins map
     */
    private Map<Coin, Integer> fillStorage(final List<Coin> coins) {
        return coins.stream().collect(Collectors.toMap(coin -> coin, coin -> DEFAULT_AMOUNT));
    }

    /**
     * This method is not thread safe, because supported coins are created on object creation and
     * cannot be modified.
     *
     * @param coin coin which has to be verified
     */
    private void assertCoin(final Coin coin) {
        if (!coinsStorage.containsKey(coin))
            throw new VendingMachineException(ExceptionType.UNSUPPORTED_COINS);
    }
}
