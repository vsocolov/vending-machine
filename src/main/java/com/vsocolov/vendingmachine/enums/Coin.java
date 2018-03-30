package com.vsocolov.vendingmachine.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Coin {
    ONE_PENNY(1),
    TWO_PENCE(2),
    FIVE_PENCE(5),
    TEN_PENCE(10),
    TWENTY_PENCE(20),
    FIFTY_PENCE(50),
    ONE_POUND(100),
    TWO_POUNDS(200);

    private final int amount;

    Coin(final int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public static Optional<Coin> getCoin(final int pennies) {
        return Arrays.stream(Coin.values())
                .filter(coin -> coin.getAmount() == pennies)
                .findFirst();
    }
}
