package com.vsocolov.vendingmachine.data;

import com.vsocolov.vendingmachine.enums.Coin;

public class Change {

    private final Coin coin;

    private final int quantity;

    public Change(final Coin coin, final int quantity) {
        this.coin = coin;
        this.quantity = quantity;
    }

    public Coin getCoin() {
        return coin;
    }

    public int getQuantity() {
        return quantity;
    }
}
