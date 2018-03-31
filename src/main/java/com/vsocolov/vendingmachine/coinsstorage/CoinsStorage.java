package com.vsocolov.vendingmachine.coinsstorage;

import com.vsocolov.vendingmachine.enums.Coin;

public interface CoinsStorage {

    int getCoinAmount(Coin coin);

    void setCoinAmount(Coin coin, int amount);
}
